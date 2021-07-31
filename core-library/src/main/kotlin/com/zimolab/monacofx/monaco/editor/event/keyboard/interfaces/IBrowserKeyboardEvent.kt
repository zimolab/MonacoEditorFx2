package com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IBrowserKeyboardEvent {
    val altKey: Boolean
    val bubbles: Boolean
    val cancelBubble: Boolean
    val cancelable: Boolean
    val charCode: Int
    val code: String
    val composed: Boolean
    val ctrlKey: Boolean
    val defaultPrevented: Boolean
    val detail: Int
    val eventPhase: Int
    val isComposing: Boolean
    val isTrusted: Boolean
    val key: String
    val keyCode: Int
    val location: Int
    val metaKey: Boolean
    val repeat: Boolean
    val returnValue: Boolean
    val shiftKey: Boolean
    val timeStamp: Int
    val type: String
    val which: Int
}
