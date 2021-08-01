package com.zimolab.monacofx.monaco.editor.event.textmodel

import com.zimolab.monacofx.monaco.Range
import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.AbstractIModelContentChange
import netscape.javascript.JSObject

class ModelContentChange(jsObject: JSObject): AbstractIModelContentChange(jsObject) {
    private val _range by lazy {
        Range(super.range as JSObject)
    }
    override val range: Range
        get() = _range
}