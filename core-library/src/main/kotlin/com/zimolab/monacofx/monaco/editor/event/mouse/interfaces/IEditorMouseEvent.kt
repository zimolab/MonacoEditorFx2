package com.zimolab.monacofx.monaco.editor.event.mouse.interfaces

import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface IEditorMouseEvent {
    val event: Any
    val target: Any
}