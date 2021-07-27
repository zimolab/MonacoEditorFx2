package com.zimolab.monacofx.jseditor

abstract class IEditorAction(
    val id: String,
    val label: String,
    val alias: String
) {

    abstract fun isSupported(): Boolean
    abstract fun run(): Any?
}