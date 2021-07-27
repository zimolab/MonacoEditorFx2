package com.zimolab.monacofx.jseditor.event.dom

interface UIEvent : Event {
    val detail: Int
    val which: Int
}