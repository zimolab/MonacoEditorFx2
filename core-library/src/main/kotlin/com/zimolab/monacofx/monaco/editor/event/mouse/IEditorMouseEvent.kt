package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface IEditorMouseEvent {
    val event: JSObject
    val target: JSObject
}