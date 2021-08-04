package com.zimolab.monacofx.monaco

import netscape.javascript.JSObject

class Selection(jsObject: JSObject): AbstractISelection(jsObject) {
    fun equalsSelection(other: Selection): Boolean {
        return super.equalsSelection(other.targetObject)
    }

    override fun setEndPosition(endLineNumber: Int, endColumn: Int): Position {
        val result = super.setEndPosition(endLineNumber, endColumn)
        if (result !is JSObject)
            throw RuntimeException("return value type is not as expected")
        return Position(result)
    }

    override fun getPosition(): Position {
        val result = super.getPosition()
        if (result !is JSObject)
            throw RuntimeException("return value type is not as expected")
        return Position(result)
    }

    override fun setStartPosition(startLineNumber: Int, startColumn: Int): Position {
        val result = super.setStartPosition(startLineNumber, startColumn)
        if (result !is JSObject)
            throw RuntimeException("return value type is not as expected")
        return Position(result)
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