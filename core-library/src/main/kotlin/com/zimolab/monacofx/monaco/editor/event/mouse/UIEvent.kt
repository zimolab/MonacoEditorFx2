package com.zimolab.monacofx.monaco.editor.event.mouse

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.monacofx.monaco.editor.Event

interface UIEvent : Event {
    @JsField
    val detail: Int
    val which: Int
}