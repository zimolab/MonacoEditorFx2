package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.annotation.JSONField
import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IRange {
    val startLineNumber: Int
    val startColumn: Int
    val endLineNumber: Int
    val endColumn: Int

    @JsFunction
    fun isEmpty(): Boolean
    @JsFunction
    fun containsPosition(position: Any): Boolean
    @JsFunction
    fun containsRange(range: Any): Boolean
    @JsFunction
    fun strictContainsRange(range: Any): Boolean
    @JsFunction
    fun plusRange(range: Any): Any
    @JsFunction
    fun intersectRanges(range: Any): Any?
    @JsFunction
    fun equalsRange(other: Any?): Boolean
    @JsFunction
    fun getEndPosition(): Any
    @JsFunction
    fun getStartPosition(): Any
    @JsFunction
    fun setEndPosition(endLineNumber: Int, endColumn: Int): Any
    @JsFunction
    fun setStartPosition(startLineNumber: Int, startColumn: Int): Any
    @JsFunction
    fun collapseToStart(): Any
}
