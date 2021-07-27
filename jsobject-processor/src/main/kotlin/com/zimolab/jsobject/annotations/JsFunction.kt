package com.zimolab.jsobject.annotations

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import netscape.javascript.JSObject

@Target(AnnotationTarget.FUNCTION)
annotation class JsFunction(val nameInJs: String = "", val exceptionOnUndefined: Boolean = EXCEPTION_ON_UNDEFINED) {
    companion object {
        const val EXCEPTION_ON_UNDEFINED = true
        val SupportedArgumentTypes = setOf<String>(
            Boolean::class.qualifiedName!!,
            Int::class.qualifiedName!!,
            Double::class.qualifiedName!!,
            String::class.qualifiedName!!,
            JSObject::class.qualifiedName!!,
            Unit::class.qualifiedName!!,

            Boolean::class.asTypeName().copy(nullable = true).toString(),
            String::class.asTypeName().copy(nullable = true).toString(),
            Int::class.asTypeName().copy(nullable = true).toString(),
            Double::class.asTypeName().copy(nullable = true).toString(),
            JSObject::class.asTypeName().copy(nullable = true).toString()
        )

        val SupportedReturnTypes = setOf(
            Boolean::class.qualifiedName!!,
            Int::class.qualifiedName!!,
            Double::class.qualifiedName!!,
            String::class.qualifiedName!!,
            JSObject::class.qualifiedName!!,
            Unit::class.qualifiedName!!,

            Boolean::class.asTypeName().copy(nullable = true).toString(),
            String::class.asTypeName().copy(nullable = true).toString(),
            Int::class.asTypeName().copy(nullable = true).toString(),
            Double::class.asTypeName().copy(nullable = true).toString(),
            JSObject::class.asTypeName().copy(nullable = true).toString()
        )
    }
}
