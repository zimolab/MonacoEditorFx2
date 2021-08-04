package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IWordAtPosition {
    val word: String
    val startColumn: Int
    val endColumn: Int
}