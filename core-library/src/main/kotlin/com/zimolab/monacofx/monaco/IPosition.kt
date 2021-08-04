package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.annotation.JSONField
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

    companion object {
        fun positionOf(column: Int, lineNumber: Int): IPosition {
            return object : IPosition{
                override val column: Int = column
                override val lineNumber: Int = lineNumber

                @JSONField(serialize = false)
                override fun with(newLineNumber: Int, newColumn: Int): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun delta(deltaLineNumber: Int, deltaColumn: Int): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun isBefore(other: Any): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun isBeforeOrEqual(other: Any): Boolean {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}
