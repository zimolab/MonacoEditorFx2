package com.zimolab.jsobject.processors

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.findArgument
import com.zimolab.jsobject.qualifiedNameStr
import com.zimolab.jsobject.simpleNameStr
import netscape.javascript.JSObject

/**
 * 代表一个被@JsField标记的字段，同时包含了字段本身的信息以及@JsField注解中携带的信息
 * @property property KSPropertyDeclaration
 * @property annotation KSAnnotation?
 * @property fieldName String
 * @property memberName String
 * @property qualifiedName String
 * @property ksType KSType
 * @property nullable Boolean
 * @property mutable Boolean
 * @property annotationArguments List<KSValueArgument>?
 * @property useInitializer Boolean
 * @property useNullInitializer Boolean
 * @property ignoreUndefined Boolean
 * @constructor
 */
class ResolvedJsField(
    val property: KSPropertyDeclaration,
    val annotation: KSAnnotation?
) {
    val fieldName: String by lazy {
        property.simpleNameStr
    }

    val memberName: String by lazy {
        annotation?.findArgument(JsField::member.name, fieldName)?: fieldName
    }

    val qualifiedName: String by lazy {
        property.qualifiedNameStr
    }

    val ksType: KSType by lazy {
        property.type.resolve()
    }

    val nullable: Boolean by lazy {
        ksType.isMarkedNullable
    }

    val mutable: Boolean by lazy {
        property.isMutable
    }

    private val annotationArguments by lazy {
        annotation?.arguments
    }

    val useInitializer: Boolean by lazy {
        val a = annotationArguments?.find {
            it.name?.asString() == JsField::useInitializer.name
        }
        if (a == null) JsField.USE_INITIALIZER else (a.value as? Boolean) == true
    }

    inline fun <reified T> initializer(): T? {
        if (nullable && useNullInitializer)
            return null
        if (nullable && T::class == JSObject::class)
            return null
        if (!nullable && T::class == JSObject::class)
            throw AnnotationProcessingError("there is no initializer for a property of non-nullable JSObject type")
        if (T::class.qualifiedName !in JsField.DefaultInitializers.keys)
            throw AnnotationProcessingError("type '${T::class.qualifiedName}' is not supported")
        return JsField.DefaultInitializers[T::class.qualifiedName] as T
    }

    fun initializer(qualifiedTypeName: String): Any? {
        if (nullable && useNullInitializer)
            return null
        if (nullable && qualifiedTypeName == JSObject::class.qualifiedName)
            return null
        if (!nullable && qualifiedTypeName == JSObject::class.qualifiedName)
            throw AnnotationProcessingError("there is no initializer for a property of non-nullable JSObject type")
        if (qualifiedTypeName !in JsField.DefaultInitializers.keys)
            throw AnnotationProcessingError("type '${qualifiedTypeName}' is not supported")
        return JsField.DefaultInitializers[qualifiedTypeName]
    }

    val useNullInitializer: Boolean by lazy {
        val a = annotationArguments?.find { it.name?.asString() == JsField::useNullInitializer.name }
        if (a == null) JsField.INIT_NULLABLE_FIELD_WITH_NULL else (a.value as? Boolean) == true
    }

    val ignoreUndefined: Boolean by lazy {
        val a = annotationArguments?.find { it.name?.asString() == JsField::ignoreUndefined.name }
        if (a == null) JsField.IGNORE_UNDEFINED else (a.value as? Boolean) == true
    }
}