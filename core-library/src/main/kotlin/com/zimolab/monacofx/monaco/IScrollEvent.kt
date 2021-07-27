package com.zimolab.monacofx.monaco

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