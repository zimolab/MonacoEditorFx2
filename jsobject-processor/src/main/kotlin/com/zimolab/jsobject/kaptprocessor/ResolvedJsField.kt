package com.zimolab.jsobject.kaptprocessor

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface
import org.jetbrains.annotations.Nullable
import javax.lang.model.element.ExecutableElement
import javax.tools.Diagnostic

class ResolvedJsField(
    val getter: ExecutableElement,
    val setter: ExecutableElement?
) {

    val fieldName: String by lazy {
        getter.propertyName()!!
    }

    val memberName: String by lazy {
        annotation?.member?: fieldName
    }

    val typeName: TypeName by lazy {
       getter.returnType.asTypeName()
    }

    val nullable: Boolean by lazy {
        getter.getAnnotationsByType(Nullable::class.java).firstOrNull() != null
    }

    val mutable: Boolean by lazy {
        setter != null
    }

    val annotation: JsField? by lazy{
        getter.getAnnotationsByType(JsField::class.java).firstOrNull()
    }

    val ignoreUndefined: Boolean by lazy {
        annotation?.ignoreUndefined?: JsField.IGNORE_UNDEFINED
    }

    val skipped: Boolean by lazy {
        annotation?.skip?: JsField.SKIP_FIELD
    }
}