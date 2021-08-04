package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

interface ITextModelUpdateOptions {
    val tabSize: Int?
    val indentSize: Int?
    val insertSpaces: Boolean?
    val trimAutoWhitespace: Boolean?
}