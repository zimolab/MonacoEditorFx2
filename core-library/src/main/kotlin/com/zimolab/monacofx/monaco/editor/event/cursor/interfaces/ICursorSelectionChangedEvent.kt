package com.zimolab.monacofx.monaco.editor.event.cursor.interfaces

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface ICursorSelectionChangedEvent {
    val selection: Any
    val secondarySelections: Any
    val modelVersionId: Int
    @JsField(ignoreUndefined = true)
    val oldSelections: Any?
    val oldModelVersionId: Int
    val source: String
    val reason: Int
}