package com.zimolab.monacofx.monaco.editor

import com.zimolab.monacofx.monaco.editor.Event

interface UIEvent : Event {
    val detail: Int
    val which: Int
}