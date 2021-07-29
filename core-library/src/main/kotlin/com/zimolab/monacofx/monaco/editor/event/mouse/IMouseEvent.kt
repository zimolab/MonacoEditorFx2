package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface IMouseEvent {
    @JsField(ignoreUndefined = true)
    var browserEvent: JSObject
    val leftButton: Boolean
    val middleButton: Boolean
    val rightButton: Boolean
    val buttons: Int
    val target: JSObject?
    val detail: Int
    val posx: Int
    val posy: Int
    val ctrlKey: Boolean
    val shiftKey: Boolean
    val altKey: Boolean
    val metaKey: Boolean
    val timestamp: Int
    fun preventDefault()
    fun stopPropagation()
}