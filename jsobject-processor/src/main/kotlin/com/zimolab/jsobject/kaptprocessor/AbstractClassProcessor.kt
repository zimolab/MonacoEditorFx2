package com.zimolab.jsobject.kaptprocessor

import javax.annotation.processing.Messager
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

object AbstractClassProcessor {
    lateinit var elementUtils: Elements
    lateinit var typeUtils: Types
    lateinit var logger: Messager

    fun initialize(elements: Elements, types: Types, messager: Messager) {
        elementUtils = elements
        typeUtils = types
        logger = messager

        logger.printMessage(Diagnostic.Kind.NOTE, "${this.javaClass.simpleName} initialize")
    }


    fun process(jsInterface: TypeElement): ResolvedJsInterface? {
        TODO()
    }
}