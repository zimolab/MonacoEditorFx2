package com.zimolab.monacofx.jsbase

import com.zimolab.monacofx.jseditor.Globals.JS_EDITOR_NAMESPACE
import netscape.javascript.JSObject

interface JsObjectInstance {
    companion object {
        const val DEFAULT_JS_CLASS_NAME_SPACE = "${JS_EDITOR_NAMESPACE}.ObjectCreator"
        fun defaultNewJsObjectCodeTemplate(instance: JsObjectInstance) = "new $DEFAULT_JS_CLASS_NAME_SPACE.${instance.jsClassName}(%s);"
    }
    val jsClassName: String
    fun get(): JSObject?
}