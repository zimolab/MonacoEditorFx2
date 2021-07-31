package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IContentSizeChangedEvent {
    val contentHeight: Int
    val contentHeightChanged: Boolean
    val contentWidth: Int
    val contentWidthChanged: Boolean
}