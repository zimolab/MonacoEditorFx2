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
    val member: String = "",
    val skip: Boolean = SKIP_FIELD,
    val keepPropertyCase: Boolean = KEEP_PROPERTY_CASE,
    val useInitializer: Boolean = USE_INITIALIZER,
    val useNullInitializer: Boolean = INIT_NULLABLE_FIELD_WITH_NULL,
    val ignoreUndefined: Boolean = IGNORE_UNDEFINED
) {
    companion object {
        const val KEEP_PROPERTY_CASE = false
        const val SKIP_FIELD = false
        const val USE_INITIALIZER = false
        const val INIT_NULLABLE_FIELD_WITH_NULL = true
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

        val DefaultInitializers = mapOf<String, Any>(
            Boolean::class.qualifiedName!! to DEFAULT_BOOLEAN_INITIALIZER,
            Boolean::class.asTypeName().copy(nullable = true).toString() to DEFAULT_BOOLEAN_INITIALIZER,

            Int::class.qualifiedName!! to DEFAULT_INT_INITIALIZER,
            Int::class.asTypeName().copy(nullable = true).toString() to DEFAULT_INT_INITIALIZER,

            Double::class.qualifiedName!! to DEFAULT_DOUBLE_INITIALIZER,
            Double::class.asTypeName().copy(nullable = true).toString() to DEFAULT_DOUBLE_INITIALIZER,

            String::class.qualifiedName!! to DEFAULT_STRING_INITIALIZER,
            String::class.asTypeName().copy(nullable = true).toString() to DEFAULT_STRING_INITIALIZER,

            JSObject::class.asTypeName().copy(nullable = true).toString() to "null"
        )
    }
}


/**
 * 在处理接口或者抽象类时，诸如setXXX(a: Type)/getXXX(): Type之类的函数可能会被误解为是一个属性，因此需要通过一些注解明确告诉处理器，
 * 不要将其理解为属性
 */

@Target(AnnotationTarget.FUNCTION)
annotation class NotSetter

@Target(AnnotationTarget.FUNCTION)
annotation class NotGetter