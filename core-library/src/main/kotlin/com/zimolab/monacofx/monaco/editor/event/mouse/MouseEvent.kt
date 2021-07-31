package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.AbstractIMouseEvent
import netscape.javascript.JSObject

class MouseEvent(jsObject: JSObject): AbstractIMouseEvent(jsObject) {
    override val browserEvent: JSObject
        get() = super.browserEvent as JSObject
}