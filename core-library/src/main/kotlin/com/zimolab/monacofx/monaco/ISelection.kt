package com.zimolab.monacofx.monaco

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface ISelection {
    val selectionStartLineNumber: Int
    val selectionStartColumn: Int
    val positionLineNumber: Int
    val positionColumn: Int

    @JsFunction
    fun equalsSelection(other: Any): Boolean
    @JsFunction
    fun getDirection(): Int
    @JsFunction
    fun setEndPosition(endLineNumber: Int, endColumn: Int): Any
    @JsFunction
    fun getPosition(): Any
    @JsFunction
    fun setStartPosition(startLineNumber: Int, startColumn: Int): Any
}