package com.zimolab.jsobject.kaptprocessor

import com.squareup.kotlinpoet.asTypeName
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.NotGetter
import com.zimolab.jsobject.annotations.NotSetter
import java.util.*
import javax.lang.model.element.ExecutableElement

fun ExecutableElement.propertyName(): String? {
    if (!isProperty())
        return null
    val annotation = getAnnotationsByType(JsField::class.java).firstOrNull()
    val keepCase = annotation?.keepPropertyCase ?: JsField.KEEP_PROPERTY_CASE
    return if (keepCase)
        simpleName.substring(3)
    else
        simpleName.substring(3).replaceFirstChar { it.lowercase(Locale.getDefault()) }
}

fun ExecutableElement.isProperty(): Boolean = isSetter() || isGetter()

fun ExecutableElement.isSetter(): Boolean {
    if (getAnnotationsByType(NotGetter::class.java).firstOrNull() != null || getAnnotationsByType(NotSetter::class.java).firstOrNull() != null)
        return false

    return  (simpleName.startsWith("set") && // 以set开头
            simpleName.length > 3 && // 长度超过3个字符
            parameters.size == 1 && // 有一个参数
            returnType.asTypeName() == Unit::class.asTypeName())// 无返回值，或者说返回值类型为void或Unit
}

fun ExecutableElement.isGetter(): Boolean {
    if (getAnnotationsByType(NotGetter::class.java).firstOrNull() != null || getAnnotationsByType(NotSetter::class.java).firstOrNull() != null)
        return false
    return (simpleName.startsWith("get") && // 以set开头
            simpleName.length > 3 && // 长度超过3个字符
            parameters.size == 0 && // 无参数
            returnType.asTypeName() != Unit::class.asTypeName()) // 有返回值
}

fun ExecutableElement.findSetter(): ExecutableElement? {
    if (!isGetter())
        return null
    val name = propertyName()?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        ?: return null
    val setterName = "set$name"
    return enclosingElement.enclosedElements.filterIsInstance<ExecutableElement>().find {
        if (it.isSetter()) {
            it.simpleName.toString() == setterName && it.parameters[0].asType().asTypeName() == returnType.asTypeName()
        } else {
            false
        }
    }
}

fun ExecutableElement.findGetter(): ExecutableElement? {
    if (!isSetter())
        return null
    val name = propertyName()?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        ?: return null
    val getterName = "get$name"
    return enclosingElement.enclosedElements.filterIsInstance<ExecutableElement>().find {
        (it.isGetter() && // 是一个getter
                it.simpleName.toString() == getterName && // 名称相符
                it.returnType == this.parameters[0].asType()) // 该getter的返回值类型与setter的入参类型一致
    }
}