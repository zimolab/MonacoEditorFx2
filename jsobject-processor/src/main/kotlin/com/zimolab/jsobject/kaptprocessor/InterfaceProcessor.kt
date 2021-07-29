package com.zimolab.jsobject.kaptprocessor

import com.squareup.kotlinpoet.TypeName
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.processors.AnnotationProcessingError
import javax.annotation.processing.Messager
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

object InterfaceProcessor {
    lateinit var elementUtils: Elements
    lateinit var typeUtils: Types
    lateinit var logger: Messager

    fun initialize(elements: Elements, types: Types, messager: Messager) {
        elementUtils = elements
        typeUtils = types
        logger = messager
    }

    // 解析接口类型
    fun process(jsInterface: TypeElement): ResolvedJsInterface {
        val packageName = elementUtils.getPackageOf(jsInterface).qualifiedName.toString()
        val interfaceName = jsInterface.simpleName.toString()
        val resolvedJsInterface = ResolvedJsInterface(
            jsInterface,
            packageName,
            interfaceName
        )
        parseJsFields(resolvedJsInterface)
        parseJsFunctions(resolvedJsInterface)
        return resolvedJsInterface
    }

    fun parseJsFields(resolvedJsInterface: ResolvedJsInterface) {
        // 在接口中，所有的属性都被实现为setter/getter函数的形式
        val allPossibilities =
            resolvedJsInterface.source.enclosedElements.filterIsInstance<ExecutableElement>().filter { it.isProperty() }
        // getter -> setter
        val properties = mutableMapOf<ExecutableElement, ExecutableElement?>()
        // 先获取getter
        allPossibilities.forEach {
            if (it.isGetter())
                properties[it] = null
        }
        logger.printMessage(Diagnostic.Kind.NOTE, "found: ${properties.keys.size} getters")
        // 再根据getter查找对应的setter
        val iterator = properties.iterator()
        var c = 0
        while (iterator.hasNext()) {
            val property = iterator.next()
            val getter = property.key
            val setter = getter.findSetter()
            property.setValue(setter)
            resolveJsFields(resolvedJsInterface, property)
        }
    }

    fun parseJsFunctions(resolvedJsInterface: ResolvedJsInterface) {

    }

    fun resolveJsFields(
        resolvedJsInterface: ResolvedJsInterface,
        property: MutableMap.MutableEntry<ExecutableElement, ExecutableElement?>
    ) {
        val getter = property.key
        val setter = property.value

        val resolvedJsField = ResolvedJsField(getter, setter)
        val annotation = getter.getAnnotation(JsField::class.java)
        logger.printMessage(Diagnostic.Kind.NOTE, "${getter.simpleName} - ${annotation}" +
                "| ${getter.getAnnotationsByType(JsField::class.java).firstOrNull()}")
        if (!resolvedJsField.skipped && !resolvedJsInterface.ignoreUnsupportedTypes) {
            if (!checkJsFieldType(resolvedJsField.typeName)) {
                throw AnnotationProcessingError("Type(${resolvedJsField.typeName}) of ${resolvedJsField.fieldName} is not supported")
            }
        }
        resolvedJsInterface.jsFields.add(resolvedJsField)
    }

    fun checkJsFieldType(typeName: TypeName): Boolean {
        return typeName.toString() in JsField.SupportedTypes
    }
}