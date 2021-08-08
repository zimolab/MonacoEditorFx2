package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.monacofx.monaco.IPosition
import com.zimolab.monacofx.monaco.IRange
import com.zimolab.monacofx.monaco.Position
import com.zimolab.monacofx.monaco.Range
import com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.AbstractIMouseTarget
import com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.IMouseTarget
import netscape.javascript.JSObject

class MouseTarget(jsObject: JSObject): AbstractIMouseTarget(jsObject) {
    private val _position by lazy {
        Position(super.position as JSObject)
    }

    private val _range by lazy {
        Range(super.range as JSObject)
    }


    override val position: Position
        get() = _position
    override val range: Range
        get() = _range
    override val detail: JSObject
        get() = super.detail as JSObject
}