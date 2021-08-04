package com.zimolab.monacofx.monaco

import com.zimolab.monacofx.monaco.Globals.JS_UNDEFINED
import netscape.javascript.JSObject

class Range(jsObject: JSObject): AbstractIRange(jsObject) {

    fun containsPosition(position: Position): Boolean {
        return super.containsPosition(position.targetObject)
    }

    fun containsRange(range: Range): Boolean {
        return super.containsRange(range.targetObject)
    }

    fun strictContainsRange(range: Range): Boolean {
        return super.strictContainsRange(range.targetObject)
    }

    fun plusRange(range: Range): Range {
        val result = super.plusRange(range.targetObject)
        if (result != JS_UNDEFINED && result is JSObject)
            return Range(result)
        throw RuntimeException("fail to call js function 'Range.plusRange(range: Range): Range'")
    }

    fun intersectRanges(range: Range): Range? {
        val result = super.intersectRanges(range)
        if (result == JS_UNDEFINED)
            throw RuntimeException("fail to call js function 'Range.intersectRanges(range: Range): Range?'")
        if (result == null)
            return result
        return Range(result as JSObject)
    }

    fun equalsRange(other: Range): Boolean {
        return super.equalsRange(other.targetObject)
    }

    override fun getEndPosition(): Position {
        val result = super.getEndPosition()
        if (result != JS_UNDEFINED && result is JSObject)
            return Position(result)
        throw RuntimeException("fail to call js function 'Range.getEndPosition(): Position'")

    }

    override fun getStartPosition(): Position {
        val result = super.getStartPosition()
        if (result != JS_UNDEFINED && result is JSObject)
            return Position(result)
        throw RuntimeException("fail to call js function 'Range.getStartPosition(): Position'")

    }

    override fun setEndPosition(endLineNumber: Int, endColumn: Int): Range {
        val result = super.setEndPosition(endLineNumber, endColumn)
        if (result != JS_UNDEFINED && result is JSObject)
            return Range(result)
        throw RuntimeException("fail to call js function 'Range.setEndPosition(endLineNumber: Int, endColumn: Int): Range'")
    }

    override fun setStartPosition(startLineNumber: Int, startColumn: Int): Range {
        val result = super.setStartPosition(startLineNumber, startColumn)
        if (result != JS_UNDEFINED && result is JSObject)
            return Range(result)
        throw RuntimeException("fail to call js function 'Range.setStartPosition(startLineNumber: Int, startColumn: Int): Range'")

    }

    override fun collapseToStart(): Range {
        val result = super.collapseToStart()
        if (result != JS_UNDEFINED && result is JSObject)
            return Range(result)
        throw RuntimeException("fail to call js function 'Range.collapseToStart()'")
    }

    override fun toSelection(): ISelection {
        return ISelection.selectionOf(startLineNumber, startColumn, endLineNumber, endColumn)
    }

    override fun toString(): String {
        return "Range@[$startLineNumber, $startColumn -> $endLineNumber, $endColumn]"
    }
}