package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface IMouseTarget{
     val type: Int
     val position: JSObject?
     val mouseColumn: Int
     val range: JSObject?
     val detail: JSObject?
}
