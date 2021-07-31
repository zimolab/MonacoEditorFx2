package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IPasteEvent {
    val range: Any
    @JsField(ignoreUndefined = true)
    val mode: String?
}