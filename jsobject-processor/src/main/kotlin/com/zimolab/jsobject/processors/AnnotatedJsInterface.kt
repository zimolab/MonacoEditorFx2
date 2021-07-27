package com.zimolab.jsobject.processors

import com.google.devtools.ksp.symbol.*
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsField.Companion.SupportedTypes
import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.jsobject.findAnnotations
import com.zimolab.jsobject.findArgument
import com.zimolab.jsobject.qualifiedName
import com.zimolab.jsobject.simpleNameStr

class AnnotatedJsInterface(
    val packageName: String,
    val interfaceName: String,
    val interfaceDeclaration: KSClassDeclaration,
    val interfaceAnnotation: KSAnnotation
) {

    companion object {
        const val GENERATED_CLASS_PREFIX = "Abs"
        const val GENERATED_CLASS_SUFFIX = ""
    }
    private var classNameGenerator: (String)->String = { interfaceName->
        "$GENERATED_CLASS_PREFIX$interfaceName$GENERATED_CLASS_SUFFIX"
    }

    val simpleName: String by lazy {
        interfaceDeclaration.simpleNameStr
    }
    val ksType: KSType by lazy {
        interfaceDeclaration.asType(emptyList())
    }
    val allFields = mutableListOf<ResolvedJsField>()
    val allJsFunctions = mutableSetOf<ResolvedJsFunction>()
    val generatedClassName: String by lazy {
        interfaceAnnotation.findArgument(JsInterface::outputClassName.name, classNameGenerator(simpleName))
    }
    val outputFilename: String by lazy {
        interfaceAnnotation.findArgument(JsInterface::outputFilename.name, generatedClassName)
    }
    val containingFile: KSFile? by lazy {
        interfaceDeclaration.containingFile
    }
    val superInterfaces: Sequence<KSTypeReference> by lazy {
        interfaceDeclaration.superTypes
    }
    val ignoreUnsupportedTypes: Boolean by lazy {
        interfaceAnnotation.findArgument(JsInterface::ignoreUnsupportedTypes.name, JsInterface.IGNORE_UNSUPPORTED_TYPES)
    }

    fun addField(field: KSPropertyDeclaration) {
        val jsFiledAnnotation = field.findAnnotations(JsField::class).firstOrNull()
        if (isSkippedField(jsFiledAnnotation)) {
            return
        }
        val resolved = ResolvedJsField(field, jsFiledAnnotation)
        if (verifyField(resolved)) {
            allFields.add(resolved)
        } else {
            if (!ignoreUnsupportedTypes)
                throw AnnotationProcessingError("type of property '${resolved.fieldName}'(${resolved.ksType.qualifiedName}) is not supported yet.")
        }
    }

    private fun isSkippedField(annotation: KSAnnotation?): Boolean {
        if (annotation == null)
            return false
        return annotation.findArgument(JsField::skip.name, JsField.SKIP_FIELD)
    }

    fun addJsFunction(function: KSFunctionDeclaration) {
        val annotation = function.findAnnotations(JsFunction::class).firstOrNull() ?: return
        val resolved = ResolvedJsFunction(function, annotation)
        if (verifyFunction(resolved)) {
            allJsFunctions.add(resolved)
        } else {
            throw AnnotationProcessingError("the argument type or return type of function '${function.simpleName.asString()}' is not supported.")
        }
    }

    fun setClassNameGenerator(gen: (interfaceName: String)->String) {
        classNameGenerator = gen
    }

    private fun verifyField(resolvedJsField: ResolvedJsField): Boolean {
        // 验证属性的类型，确保其为受支持的类型
        return resolvedJsField.ksType.qualifiedName in SupportedTypes
    }

    private fun verifyFunction(resolved: ResolvedJsFunction): Boolean {
        // 主要检查两方面信息
        // 1. 返回值类型
        if (resolved.returnType.qualifiedName !in JsFunction.SupportedReturnTypes)
            return false
        // 2. 参数列表类型
        resolved.parameters.forEach { param ->
            if (param.type.qualifiedName !in JsFunction.SupportedArgumentTypes)
                return false
        }
        return true
    }
}