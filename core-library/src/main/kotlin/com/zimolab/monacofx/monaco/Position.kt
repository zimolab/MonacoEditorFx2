package com.zimolab.monacofx.monaco


abstract class Position(
    val column: Int? = null,
    val lineNumber: Int? = null
) {
    abstract fun with(newLineNumber: Int, newColumn: Int): Position
    abstract fun delta(deltaLineNumber: Int, deltaColumn: Int): Position
    abstract fun isBefore(other: Position): Boolean
    abstract fun isBeforeOrEqual(other: Position): Boolean
}
