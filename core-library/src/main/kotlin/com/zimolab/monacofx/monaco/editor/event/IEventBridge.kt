package com.zimolab.monacofx.monaco.editor.event

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IEventBridge {
    @JsFunction
    fun listen(eventId: Int): Boolean
    @JsFunction
    fun unlisten(eventId: Int): Boolean
    @JsFunction
    fun isListened(eventId: Int): Boolean
}