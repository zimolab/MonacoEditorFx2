package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.annotation.JSONField
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

    fun toRange(): IRange

    companion object {
        fun fromRange(range: IRange): ISelection {
            return selectionOf(range.startLineNumber, range.startColumn, range.endLineNumber, range.endColumn)
        }

        fun selectionOf(
            selectionStartLineNumber: Int,
            selectionStartColumn: Int,
            positionLineNumber: Int,
            positionColumn: Int
        ): ISelection {
            return object : ISelection {
                override val selectionStartLineNumber: Int = selectionStartLineNumber
                override val selectionStartColumn: Int = selectionStartColumn
                override val positionLineNumber: Int = positionLineNumber
                override val positionColumn: Int = positionColumn

                @JSONField(serialize = false)
                override fun equalsSelection(other: Any): Boolean {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun getDirection(): Int {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun setEndPosition(endLineNumber: Int, endColumn: Int): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun getPosition(): Any {
                    TODO("Not yet implemented")
                }
                @JSONField(serialize = false)
                override fun setStartPosition(startLineNumber: Int, startColumn: Int): Any {
                    TODO("Not yet implemented")
                }

                override fun toRange(): IRange {
                    return IRange.rangeOf(selectionStartLineNumber, selectionStartColumn, positionLineNumber, positionColumn)
                }

                override fun toString(): String {
                    return "Selection@" +
                            "[$selectionStartLineNumber, $selectionStartColumn -> " +
                            "$positionLineNumber, $positionColumn]"
                }
            }
        }
    }
}