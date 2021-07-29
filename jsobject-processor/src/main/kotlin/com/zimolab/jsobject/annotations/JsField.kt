package com.zimolab.jsobject.annotations

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.zimolab.jsobject.asKSType
import netscape.javascript.JSObject
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class JsField(
    val jsMemberName: String = "",
    val skip: Boolean = SKIP_FIELD,
    val ignoreUndefined: Boolean = IGNORE_UNDEFINED
) {
    companion object {
        const val SKIP_FIELD = false
        const val IGNORE_UNDEFINED = true
        const val DEFAULT_INT_INITIALIZER = 0
        const val DEFAULT_STRING_INITIALIZER = ""
        const val DEFAULT_BOOLEAN_INITIALIZER = false
        const val DEFAULT_DOUBLE_INITIALIZER = 0.0

        val SupportedTypes = listOf<String>(
            Boolean::class.qualifiedName!!,
            Int::class.qualifiedName!!,
            Double::class.qualifiedName!!,
            String::class.qualifiedName!!,
            JSObject::class.qualifiedName!!,

            Boolean::class.asTypeName().copy(nullable = true).toString(),
            String::class.asTypeName().copy(nullable = true).toString(),
            Int::class.asTypeName().copy(nullable = true).toString(),
            Double::class.asTypeName().copy(nullable = true).toString(),
            JSObject::class.asTypeName().copy(nullable = true).toString()
        )

        val DefaultInitializers = mapOf<String, String>(
            Boolean::class.qualifiedName!! to DEFAULT_BOOLEAN_INITIALIZER.toString(),
            Boolean::class.asTypeName().copy(nullable = true).toString() to DEFAULT_BOOLEAN_INITIALIZER.toString(),

            Int::class.qualifiedName!! to DEFAULT_INT_INITIALIZER.toString(),
            Int::class.asTypeName().copy(nullable = true).toString() to DEFAULT_INT_INITIALIZER.toString(),

            Double::class.qualifiedName!! to DEFAULT_DOUBLE_INITIALIZER.toString(),
            Double::class.asTypeName().copy(nullable = true).toString() to DEFAULT_DOUBLE_INITIALIZER.toString(),

            String::class.qualifiedName!! to DEFAULT_STRING_INITIALIZER,
            String::class.asTypeName().copy(nullable = true).toString() to DEFAULT_STRING_INITIALIZER,

            JSObject::class.asTypeName().copy(nullable = true).toString() to "null"
        )
    }
}

