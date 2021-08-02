package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.JSON
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

    companion object {
        fun dataObject(startLineNumber: Int,
                 startColumn: Int,
                 endLineNumber: Int,
                 endColumn: Int): IRange {
            return object : IRange{
                override val startLineNumber: Int = startLineNumber
                override val startColumn: Int = startColumn
                override val endLineNumber: Int = endLineNumber
                override val endColumn: Int = endColumn
                @JSONField(serialize = false)
                override fun isEmpty(): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun containsPosition(position: Any): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun containsRange(range: Any): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun strictContainsRange(range: Any): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun plusRange(range: Any): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun intersectRanges(range: Any): Any? {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun equalsRange(other: Any?): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun getEndPosition(): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun getStartPosition(): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun setEndPosition(endLineNumber: Int, endColumn: Int): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun setStartPosition(startLineNumber: Int, startColumn: Int): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun collapseToStart(): Any {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}
