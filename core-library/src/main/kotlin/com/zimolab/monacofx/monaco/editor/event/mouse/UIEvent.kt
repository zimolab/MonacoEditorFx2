package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.monacofx.monaco.editor.Event

@JsInterface
interface UIEvent : Event {
    @JsField
    val detail: Int
    @JsField(keepPropertyCase = true, member = "aaaaa")
    val Which: Int

    fun getWhich(): Double
}