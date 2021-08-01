package com.zimolab.monacofx.monaco.editor.textmodel

interface ITextModelUpdateOptions {
    var tabSize: Int?
    var indentSize: Int?
    var insertSpaces: Boolean?
    var trimAutoWhitespace: Boolean?
}
