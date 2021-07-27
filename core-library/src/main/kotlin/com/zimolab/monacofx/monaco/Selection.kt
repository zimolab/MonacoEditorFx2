package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.annotation.JSONField
import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.jsbase.JsObjectInstance
import com.zimolab.monacofx.monaco.Globals.JS_UNDEFINED
import netscape.javascript.JSObject

class Selection(
    @JSONField(serialize = false)
    private val context: MonacoEditorFx,
    val selectionStartLineNumber: Int,
    val selectionStartColumn: Int,
    val positionLineNumber: Int,
    val positionColumn: Int,
): JsObjectInstance {
    @JSONField(serialize = false)
    override val jsClassName: String = "Selection"
    @JSONField(serialize = false)
    private var jsObject: JSObject? = null

    init {
        val jsCode = JsObjectInstance.defaultNewJsObjectCodeTemplate(this).format(
            "$selectionStartLineNumber, $selectionStartColumn, $positionLineNumber, $positionColumn"
        )
        val result = context.editor.execute(jsCode)
        jsObject = if (result != JS_UNDEFINED && result is JSObject)
            result
        else
            null
    }

    override fun get(): JSObject? = jsObject
}
