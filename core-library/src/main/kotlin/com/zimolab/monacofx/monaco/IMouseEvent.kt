package com.zimolab.monacofx.monaco

import netscape.javascript.JSObject

interface  IMouseEvent {
    val browserEvent: JSObject
    val leftButton: Boolean
    val middleButton: Boolean
    val rightButton: Boolean
    val buttons: Number
    val target: JSObject
    val detail: Number
    val posx: Number
    val posy: Number
    val ctrlKey: Boolean
    val shiftKey: Boolean
    val altKey: Boolean
    val metaKey: Boolean
    val timestamp: Number
    fun preventDefault()
    fun stopPropagation()
}
