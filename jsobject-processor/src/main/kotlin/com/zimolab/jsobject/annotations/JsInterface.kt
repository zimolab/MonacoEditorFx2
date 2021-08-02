package com.zimolab.jsobject.annotations

import java.lang.annotation.ElementType

@Target(AnnotationTarget.CLASS)
annotation class JsInterface(
    val outputClassName: String = "",
    val outputFilename: String = "",
    val ignoreUnsupportedTypes: Boolean = IGNORE_UNSUPPORTED_TYPES,
    val newFunction: Boolean = NEW_INSTANCE_FUNCTION
) {

    companion object {
        const val NEW_INSTANCE_FUNCTION = true
        const val IGNORE_UNSUPPORTED_TYPES = true
    }
}
