package com.zimolab.jsobject.processors

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.jsobject.findAnnotations
import com.zimolab.jsobject.packageNameStr
import com.zimolab.jsobject.qualifiedNameStr


class JsInterfaceProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger,
    val options: Map<String, String>
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(checkNotNull(JsInterface::class.qualifiedName))
            .filterIsInstance<KSClassDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        JsClassFileGenerator.initialize(codeGenerator, logger, options)
        JsClassFileGenerator.onTaskDone { success, exception ->
            if (success) {
                logger.info("class file generated", )
            }
            if (exception != null) {
                throw exception
            }
        }

        symbols.filter { it.validate() }.forEach { ksAnnotated ->
            if (ksAnnotated.classKind != ClassKind.INTERFACE)
                throw AnnotationProcessingError("@${JsInterface::class.simpleName} can only be applied on interfaces.")
            ksAnnotated.accept(JsInterfaceVisitor(resolver, codeGenerator, logger, options), Unit)
        }

        return symbols.filter { !it.validate() }.toList()

    }

    inner class JsInterfaceVisitor(private val resolver: Resolver,codeGenerator: CodeGenerator, private val logger: KSPLogger, private val options: Map<String, String>) : KSVisitorVoid() {
        lateinit var interfaceName: String
        lateinit var packageName: String
        lateinit var annotatedJsInterface: AnnotatedJsInterface

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val qualifiedName = classDeclaration.qualifiedNameStr

            interfaceName = qualifiedName
            packageName = classDeclaration.packageNameStr

            val interfaceAnnotation = classDeclaration.findAnnotations(JsInterface::class).firstOrNull()
                ?: throw AnnotationProcessingError("interface '$interfaceName' has no @JsFunction annotation.")
            annotatedJsInterface =
                AnnotatedJsInterface(packageName, interfaceName, classDeclaration, interfaceAnnotation)
            // 处理接口中定义的属性（字段）
            classDeclaration.getDeclaredProperties().asSequence().forEach {
                annotatedJsInterface.addField(it)
            }
            // 处理接口中定义的函数
            classDeclaration.getDeclaredFunctions().asSequence().forEach {
                annotatedJsInterface.addJsFunction(it)
            }
            // 将annotatedJsInterface对象发送到后台IO线程，生成文件
            JsClassFileGenerator.submit(annotatedJsInterface)
        }
    }

    /**
     * Called by Kotlin Symbol Processing to finalize the processing of a compilation.
     */
    override fun finish() {
        super.finish()
        JsClassFileGenerator.shutdownNow()
    }

    /**
     * Called by Kotlin Symbol Processing to handle errors after a round of processing.
     */
    override fun onError() {
        super.onError()
        JsClassFileGenerator.shutdownNow()
    }
}

class JsInterfaceProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return JsInterfaceProcessor(environment.codeGenerator, environment.logger, environment.options)
    }
}

class AnnotationProcessingError(message: String) : Throwable(message)