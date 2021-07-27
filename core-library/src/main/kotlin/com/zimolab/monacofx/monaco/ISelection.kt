package com.zimolab.monacofx.monaco

interface ISelection {
    val selectionStartLineNumber: Int
    val selectionStartColumn: Int
    val positionLineNumber: Int
    val positionColumn: Int
}