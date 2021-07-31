package com.zimolab.monacofx.monaco.editor.event.model.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IModelOptionsChangedEvent {
    val tabSize: Boolean
    val indentSize: Boolean
    val insertSpaces: Boolean
    val trimAutoWhitespace: Boolean
}

