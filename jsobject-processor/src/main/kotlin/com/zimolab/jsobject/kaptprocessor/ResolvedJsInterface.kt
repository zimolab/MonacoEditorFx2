package com.zimolab.jsobject.kaptprocessor

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

class ResolvedJsInterface(
    val source: TypeElement,
    val packageName: String,
    val outputPrefix: String = "",
    val outputSuffix: String = ""
) {
    val interfaceName:String by lazy {
        source.simpleName.toString()
    }

    val qualifiedInterfaceName: String by lazy {
        source.qualifiedName.toString()
    }

    val typeName: TypeName by lazy {
        source.asType().asTypeName()
    }

    val jsFields: MutableList<ResolvedJsField> by lazy {
        mutableListOf()
    }

    val jsFunctions: MutableList<ResolvedJsField> by lazy {
        mutableListOf()
    }

    val superInterfaces: List<TypeMirror> by lazy {
        source.interfaces
    }

    val annotation: JsInterface by lazy {
        source.getAnnotationsByType(JsInterface::class.java).first()
    }

    val outputClassName: String by lazy {
        if (annotation.outputClassName == "")
            "$outputPrefix$interfaceName$outputSuffix"
        else
            annotation.outputClassName
    }

    val outputFileName: String by lazy {
        if (annotation.outputFilename == "")
            outputClassName
        else
            annotation.outputFilename
    }

    val ignoreUnsupportedTypes: Boolean by lazy {
        annotation.ignoreUnsupportedTypes
    }
}