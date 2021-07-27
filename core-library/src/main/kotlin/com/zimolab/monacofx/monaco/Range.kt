package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.annotation.JSONField
import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.jsbase.JsObjectInstance
import com.zimolab.monacofx.monaco.Globals.JS_UNDEFINED
import netscape.javascript.JSObject

open class Range(
    @JSONField(serialize = false)
    private val context: MonacoEditorFx,
    final override val startLineNumber: Int,
    final override val startColumn: Int,
    final override val endLineNumber: Int,
    final override val endColumn: Int
) : IRange, JsObjectInstance {
    @JSONField(serialize = false)
    override val jsClassName: String = "Range"
    @JSONField(serialize = false)
    private var jsObject: JSObject? = null

    init {
        val jsCode = JsObjectInstance.defaultNewJsObjectCodeTemplate(this).format(
            "$startLineNumber, $startColumn, $endLineNumber, $endColumn"
        )
        val result = context.editor.execute(jsCode)
        jsObject = if (result != JS_UNDEFINED && result is JSObject)
            result
        else
            null
    }

    override fun get() = jsObject
    fun context(): MonacoEditorFx = context
}