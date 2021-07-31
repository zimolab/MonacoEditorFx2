package com.zimolab.monacofx.monaco.editor.event.model.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IModelContentChangedEvent {
    val changes: Any
    val eol: String
    val versionId: Int
    val isUndoing: Boolean
    val isRedoing: Boolean
    val isFlush: Boolean
}