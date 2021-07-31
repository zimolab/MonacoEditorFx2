package com.zimolab.monacofx.monaco.editor.event.keyboard

import com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces.AbstractIKeyboardEvent
import com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces.IBrowserKeyboardEvent
import netscape.javascript.JSObject

class KeyBoardEvent(jsObject: JSObject): AbstractIKeyboardEvent(jsObject) {
    private val _browserEvent by lazy {
        BrowserKeyBoardEvent(super.browserEvent as JSObject)
    }
    override val browserEvent: IBrowserKeyboardEvent
        get() = _browserEvent
}