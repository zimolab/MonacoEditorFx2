package com.zimolab.monacofx.monaco

object Globals {
    const val JS_GLOBAL_OBJECT = "window"
    const val JS_EDITOR_NAMESPACE = "$JS_GLOBAL_OBJECT.ME"
    const val JS_HOST_ENV_READY_EVENT = "fireHostEnvReadyEvent"
    const val JS_CHECK_INTERVAL = 100L
    const val DEFAULT_LOAD_TIMEOUT = 5000L
    const val DEFAULT_MONACO_EDITOR_INDEX = "/dist/index.html"
    const val JS_EXCEPTION = "JsExecuteException"
    const val ILLEGAL_JS_ID_EXCEPTION = "IllegalJsIdentifierException"
    const val JS_NULL = "null"
    const val JS_UNDEFINED = "undefined"
}