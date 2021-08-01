package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface TextModelResolvedOptions {
     val _textModelResolvedOptionsBrand: Unit
     val tabSize: Int
     val indentSize: Int
     val insertSpaces: Boolean
     val defaultEOL: Int
     val trimAutoWhitespace: Boolean
}