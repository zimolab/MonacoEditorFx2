package com.zimolab.monacofx.monaco.editor.event.cursor

import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.monaco.Position
import com.zimolab.monacofx.monaco.editor.event.cursor.interfaces.AbstractICursorPositionChangedEvent
import netscape.javascript.JSObject

class CursorPositionChangedEvent(jsObject: JSObject): AbstractICursorPositionChangedEvent(jsObject) {
    private val _position by lazy {
        Position(super.position as JSObject)
    }

    private val _secondaryPositions by lazy {
        JsArray(super.secondaryPositions as JSObject)
    }

    override val position: Position
        get() = _position
    override val secondaryPositions: JsArray
        get() = _secondaryPositions
}