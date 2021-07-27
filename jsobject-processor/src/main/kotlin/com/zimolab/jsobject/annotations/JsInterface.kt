package com.zimolab.jsobject.annotations

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
