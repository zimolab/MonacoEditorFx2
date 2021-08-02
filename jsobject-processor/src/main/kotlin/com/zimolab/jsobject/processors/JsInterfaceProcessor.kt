package com.zimolab.jsobject.processors

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.jsobject.annotations.JsInterfaceObject
import com.zimolab.jsobject.asKSType
import com.zimolab.jsobject.findAnnotations
import com.zimolab.jsobject.packageNameStr
import com.zimolab.jsobject.qualifiedNameStr


class JsInterfaceProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger,
    val options: Map<String, String>
) : SymbolProcessor {
    companion object {
        const val OPTION_NAME_OUTCLASS_PREFIX = "output_class_prefix"
        const val OPTION_NAME_OUTCLASS_SUFFIX = "output_class_suffix"
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(checkNotNull(JsInterface::class.qualifiedName))
            .filterIsInstance<KSClassDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        ClassFileGenerator.initialize(codeGenerator, logger, options)

        symbols.filter { it.validate() }.forEach { ksAnnotated ->
            if (ksAnnotated.classKind != ClassKind.INTERFACE)
                throw AnnotationProcessingError("@${JsInterface::class.simpleName} can only be applied on interfaces.")
            ksAnnotated.accept(JsInterfaceVisitor(resolver), Unit)
        }

        return symbols.filter { !it.validate() }.toList()

    }

    inner class JsInterfaceVisitor(val resolver: Resolver) : KSVisitorVoid() {
        lateinit var interfaceName: String
        lateinit var packageName: String
        lateinit var resolvedJsInterface: ResolvedJsInterface

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val qualifiedName = classDeclaration.qualifiedNameStr

            interfaceName = qualifiedName
            packageName = classDeclaration.packageNameStr

            val interfaceAnnotation = classDeclaration.findAnnotations(JsInterface::class).firstOrNull()
                ?: throw AnnotationProcessingError("interface '$interfaceName' has no @JsFunction annotation.")
            resolvedJsInterface =
                ResolvedJsInterface(packageName, interfaceName, classDeclaration, interfaceAnnotation)
            // 处理接口中定义的属性（字段）
            classDeclaration.getDeclaredProperties().asSequence().forEach {
                resolvedJsInterface.addField(it)
            }
            // 处理接口中定义的函数
            classDeclaration.getDeclaredFunctions().asSequence().forEach {
                resolvedJsInterface.addJsFunction(it)
            }
            val superInterface = JsInterfaceObject::class

            ClassFileGenerator.submit(resolvedJsInterface, superInterface)
        }
    }

}

class JsInterfaceProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return JsInterfaceProcessor(environment.codeGenerator, environment.logger, environment.options)
    }
}

class AnnotationProcessingError(message: String) : Throwable(message)