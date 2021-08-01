package com.zimolab.monacofx.monaco

import com.alibaba.fastjson.annotation.JSONField

class RangeObject(
    override val startLineNumber: Int,
    override val startColumn: Int,
    override val endLineNumber: Int,
    override val endColumn: Int
) : IRange {
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