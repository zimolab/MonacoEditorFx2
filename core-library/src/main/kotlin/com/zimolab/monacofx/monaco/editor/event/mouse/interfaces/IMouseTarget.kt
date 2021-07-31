package com.zimolab.monacofx.monaco.editor.event.mouse.interfaces

import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface IMouseTarget{
     val type: Int
     val position: Any
     val mouseColumn: Int
     val range: Any
     val detail: Any
}