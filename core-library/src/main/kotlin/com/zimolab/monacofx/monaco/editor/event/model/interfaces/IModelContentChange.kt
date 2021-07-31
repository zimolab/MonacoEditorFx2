package com.zimolab.monacofx.monaco.editor.event.model.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IModelContentChange {
    val range: Any
    val rangeOffset: Int
    val rangeLength: Int
    val text: String
}

