package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IEditorAction {
    val id: String
    val label: String
    val alias: String

    @JsFunction
    fun isSupported(): Boolean
    @JsFunction
    fun run(): Any?
}