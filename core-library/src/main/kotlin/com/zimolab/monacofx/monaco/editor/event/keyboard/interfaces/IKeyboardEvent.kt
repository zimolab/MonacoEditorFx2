package com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IKeyboardEvent {
    companion object {
        const val _standardKeyboardEventBrand = true
    }
    val browserEvent: Any
    val ctrlKey: Boolean
    val shiftKey: Boolean
    val altKey: Boolean
    val metaKey: Boolean
    val keyCode: Int
    val code: String
    val target: Any
    @JsFunction
    fun preventDefault()
    @JsFunction
    fun stopPropagation()
}