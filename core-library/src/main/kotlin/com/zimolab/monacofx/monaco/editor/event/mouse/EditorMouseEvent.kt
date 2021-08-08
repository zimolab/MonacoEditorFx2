package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.AbstractIEditorMouseEvent
import com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.IMouseEvent
import com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.IMouseTarget
import netscape.javascript.JSObject

class EditorMouseEvent(jsObject: JSObject) : AbstractIEditorMouseEvent(jsObject) {
    private val _event by lazy {
        MouseEvent(super.event as JSObject)
    }
    private val _target by lazy {
        MouseTarget(super.target as JSObject)
    }

    override val event: MouseEvent
        get() = _event
    override val target: MouseTarget
        get() = _target
}