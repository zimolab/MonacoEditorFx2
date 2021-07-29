package com.zimolab.monacofx.monaco

import netscape.javascript.JSObject


interface Position {
    val column: Int?
    val lineNumber: Int?
    fun with(newLineNumber: Int, newColumn: Int): JSObject
    fun delta(deltaLineNumber: Int, deltaColumn: Int): JSObject
    fun isBefore(other: JSObject): Boolean
    fun isBeforeOrEqual(other: JSObject): Boolean
}
