package com.zimolab.jsobject.annotations

import java.lang.annotation.ElementType

@Target(AnnotationTarget.CLASS)
annotation class JsInterface(
    val outputClassName: String = "",
    val outputFilename: String = "",
    val ignoreUnsupportedTypes: Boolean = IGNORE_UNSUPPORTED_TYPES
) {
    companion object {
        const val IGNORE_UNSUPPORTED_TYPES = false
    }
}
