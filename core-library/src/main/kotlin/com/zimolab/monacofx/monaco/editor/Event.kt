package com.zimolab.monacofx.monaco.editor

interface Event {
    val bubbles: Boolean;
    var cancelBubble: Boolean;
    val cancelable: Boolean;
    val composed: Boolean;
    val defaultPrevented: Boolean;
    val eventPhase: Int;
    val isTrusted: Boolean;
    var returnValue: Boolean;
    val timeStamp: Number;
    val type: String;

    fun preventDefault()
    fun stopImmediatePropagation()
    fun stopPropagation()

    val AT_TARGET: Int;
    val BUBBLING_PHASE: Int;
    val CAPTURING_PHASE: Int;
    val NONE: Int;

}