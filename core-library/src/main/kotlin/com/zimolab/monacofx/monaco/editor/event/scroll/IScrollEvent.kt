package com.zimolab.monacofx.monaco.editor.event.scroll

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IScrollEvent {
    val scrollTop: Int
    val scrollLeft: Int
    val scrollWidth: Int
    val scrollHeight: Int
    val scrollTopChanged: Boolean
    val scrollLeftChanged: Boolean
    val scrollWidthChanged: Boolean
    val scrollHeightChanged: Boolean
}