package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IScrolledVisiblePosition {
    val top: Int
    val left: Int
    val height: Int
}

