package com.zimolab.monacofx.monaco.editor.event.miscellaneous

import com.zimolab.monacofx.monaco.IRange
import com.zimolab.monacofx.monaco.Range
import com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.AbstractIPasteEvent
import netscape.javascript.JSObject

class PasteEvent(jsObject: JSObject): AbstractIPasteEvent(jsObject) {
    val _range by lazy {
        Range(super.range as JSObject)
    }

    override val range: Range
        get() = _range

}