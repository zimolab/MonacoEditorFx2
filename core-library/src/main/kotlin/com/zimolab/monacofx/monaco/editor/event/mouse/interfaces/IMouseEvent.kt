package com.zimolab.monacofx.monaco.editor.event.mouse.interfaces

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface IMouseEvent {
    val browserEvent: Any
    val leftButton: Boolean
    val middleButton: Boolean
    val rightButton: Boolean
    val buttons: Int
    val target: Any
    val detail: Int
    val posx: Int
    val posy: Int
    val ctrlKey: Boolean
    val shiftKey: Boolean
    val altKey: Boolean
    val metaKey: Boolean
    val timestamp: Double
    @JsFunction
    fun preventDefault()
    @JsFunction
    fun stopPropagation()
}