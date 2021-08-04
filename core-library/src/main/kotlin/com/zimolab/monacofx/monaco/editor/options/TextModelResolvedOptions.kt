package com.zimolab.monacofx.monaco.editor.options

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface ITextModelResolvedOptions {
     val _textModelResolvedOptionsBrand: Unit
     val tabSize: Int
     val indentSize: Int
     val insertSpaces: Boolean
     val defaultEOL: Int
     val trimAutoWhitespace: Boolean
}

