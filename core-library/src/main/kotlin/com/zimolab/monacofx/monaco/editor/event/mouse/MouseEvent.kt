package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.jsobject.annotations.JsInterface
import netscape.javascript.JSObject

@JsInterface(ignoreUnsupportedTypes = true)
interface MouseEvent: UIEvent {
    var abc: JSObject?
    val altKey: Boolean?
    val button: Int
    val buttons: Int
    val clientX: Int
    val clientY: Int
    val ctrlKey: Boolean
    val metaKey: Boolean
    val movementX: Int
    val movementY: Int
    val offsetX: Int
    val offsetY: Int
    val pageX: Int
    val pageY: Int
    val screenX: Int
    val screenY: Int
    val shiftKey: Boolean
    val x: Int
    val y: Int
    fun getModifierState(keyArg: String): Boolean
}