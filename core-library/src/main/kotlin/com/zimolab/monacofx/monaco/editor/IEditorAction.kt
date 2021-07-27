package com.zimolab.monacofx.monaco.editor

abstract class IEditorAction(
    val id: String,
    val label: String,
    val alias: String
) {

    abstract fun isSupported(): Boolean
    abstract fun run(): Any?
}