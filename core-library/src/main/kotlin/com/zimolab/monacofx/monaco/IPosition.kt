package com.zimolab.monacofx.monaco

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IPosition {
    val column: Int
    val lineNumber: Int
    @JsFunction
    fun with(newLineNumber: Int, newColumn: Int): Any
    @JsFunction
    fun delta(deltaLineNumber: Int, deltaColumn: Int): Any
    @JsFunction
    fun isBefore(other: Any): Boolean
    @JsFunction
    fun isBeforeOrEqual(other: Any): Boolean
}
