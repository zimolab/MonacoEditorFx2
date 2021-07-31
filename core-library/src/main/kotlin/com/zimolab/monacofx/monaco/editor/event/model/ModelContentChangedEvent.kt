package com.zimolab.monacofx.monaco.editor.event.model

import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.monaco.editor.event.model.interfaces.AbstractIModelContentChangedEvent
import netscape.javascript.JSObject

class ModelContentChangedEvent(jsObject: JSObject): AbstractIModelContentChangedEvent(jsObject) {
    private val _changes by lazy {
        JsArray(super.changes as JSObject)
    }
    override val changes: JsArray
        get() = _changes
}