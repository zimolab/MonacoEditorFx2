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

class ResolvedJsInterface(
    val packageName: String,
    val interfaceName: String,
    val interfaceDeclaration: KSClassDeclaration,
    val interfaceAnnotation: KSAnnotation
) {

    companion object {
        const val DEFAULT_OUTPUT_CLASS_PREFIX = "Abstract"
        const val DEFAULT_OUTPUT_CLASS_SUFFIX = ""
    }

    private var outputClassPrefix: String = DEFAULT_OUTPUT_CLASS_PREFIX
    private var outputClassSuffix: String = DEFAULT_OUTPUT_CLASS_SUFFIX

    private var classNameGenerator: (String)->String = { interfaceName->
        "$outputClassPrefix$interfaceName$outputClassSuffix"
    }

    val jsFields = mutableListOf<ResolvedJsField>()
    val jsFunctions = mutableSetOf<ResolvedJsFunction>()

    val simpleName: String by lazy {
        interfaceDeclaration.simpleNameStr
    }

    val ksType: KSType by lazy {
        interfaceDeclaration.asType(emptyList())
    }

    val outputClassName: String by lazy {
        interfaceAnnotation.findArgument(JsInterface::outputClassName.name, classNameGenerator(simpleName))
    }

    val outputFilename: String by lazy {
        interfaceAnnotation.findArgument(JsInterface::outputFilename.name, outputClassName)
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

    val generateNewInstanceFunction: Boolean by lazy {
        interfaceAnnotation.findArgument(JsInterface::newFunction.name, JsInterface.NEW_INSTANCE_FUNCTION)
    }

    fun setOutputClassNameRule(prefix: String, suffix: String) {
        if (prefix == "" && suffix == "")
            return
        outputClassPrefix = prefix
        outputClassSuffix = suffix
    }

    fun addField(field: KSPropertyDeclaration) {
        val jsFiledAnnotation = field.findAnnotations(JsField::class).firstOrNull()
        // 标记为@JsField(skip=true)的属性不添加
        if (isSkippedField(jsFiledAnnotation)) {
            return
        }

        val resolved = ResolvedJsField(field, jsFiledAnnotation)
        if (verifyField(resolved)) {
            jsFields.add(resolved)
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
            jsFunctions.add(resolved)
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