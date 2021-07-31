package com.zimolab.monacofx.monaco.editor.event.cursor.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface ICursorPositionChangedEvent {
    val position: Any
    val secondaryPositions: Any
    val reason: Int
    val source: String
}