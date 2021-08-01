package com.zimolab.monacofx.monaco.editor

import com.alibaba.fastjson.annotation.JSONField
import com.zimolab.monacofx.monaco.ISelection

class SelectionObject(
    override val selectionStartLineNumber: Int,
    override val selectionStartColumn: Int,
    override val positionLineNumber: Int,
    override val positionColumn: Int
) : ISelection {
    @JSONField(serialize = false)
    override fun equalsSelection(other: Any): Boolean {
        throw RuntimeException("No implementation")
    }
    @JSONField(serialize = false)
    override fun getDirection(): Int {
        throw RuntimeException("No implementation")
    }
    @JSONField(serialize = false)
    override fun setEndPosition(endLineNumber: Int, endColumn: Int): Any {
        throw RuntimeException("No implementation")
    }
    @JSONField(serialize = false)
    override fun getPosition(): Any {
        throw RuntimeException("No implementation")
    }
    @JSONField(serialize = false)
    override fun setStartPosition(startLineNumber: Int, startColumn: Int): Any {
        throw RuntimeException("No implementation")
    }
}