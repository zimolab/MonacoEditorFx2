package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

import com.zimolab.monacofx.monaco.editor.textmodel.TextModelEventListener

interface ITextModelEventProcessor {
    fun listen(eventId: Int, callback: TextModelEventListener)
    fun unlisten(eventId: Int)
    fun isListened(eventId: Int): Boolean
}