package com.zimolab.monacofx.monaco.editor.textmodel

interface IWordAtPosition {
    var word: String
    var startColumn: Int
    var endColumn: Int
}