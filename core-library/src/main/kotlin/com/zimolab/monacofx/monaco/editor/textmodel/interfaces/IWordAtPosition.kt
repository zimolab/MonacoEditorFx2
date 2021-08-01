package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

interface IWordAtPosition {
    var word: String
    var startColumn: Int
    var endColumn: Int
}