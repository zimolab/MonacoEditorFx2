package com.zimolab.monacofx.monaco.editor.event.cursor

import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.monaco.Selection
import com.zimolab.monacofx.monaco.editor.event.cursor.interfaces.AbstractICursorSelectionChangedEvent
import netscape.javascript.JSObject

class CursorSelectionChangedEvent(jsObject: JSObject) : AbstractICursorSelectionChangedEvent(jsObject) {
    private val _selection by lazy {
        Selection(super.selection as JSObject)
    }

    private val _secondarySelections by lazy {
        JsArray(super.secondarySelections as JSObject)
    }

    private val _oldSelections by lazy {
        val r = super.oldSelections
        if (r is JSObject)
            JsArray(r)
        else
            null
    }


    override val selection: Selection
        get() = _selection
    override val secondarySelections: JsArray
        get() = _secondarySelections
    override val oldSelections: JsArray?
        get() = _oldSelections
}