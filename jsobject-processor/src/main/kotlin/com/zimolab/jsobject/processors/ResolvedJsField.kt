package com.zimolab.jsobject.processors

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.findArgument
import com.zimolab.jsobject.qualifiedNameStr
import com.zimolab.jsobject.simpleNameStr

class ResolvedJsField(
    val property: KSPropertyDeclaration,
    val annotation: KSAnnotation?
) {
    val fieldName: String by lazy {
        property.simpleNameStr
    }

    val memberName: String by lazy {
        annotation?.findArgument(JsField::jsMemberName.name, fieldName)?: fieldName
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

    val ignoreUndefined: Boolean by lazy {
        annotation?.findArgument(JsField::ignoreUndefined.name, JsField.IGNORE_UNDEFINED) == true
    }
}