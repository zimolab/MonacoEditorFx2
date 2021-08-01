package com.zimolab.monacofx.monaco.editor

interface IEditorEventProcessor {
    fun listen(eventId: Int, callback: (eventId: Int, e: Any?) -> Any?)
    fun unlisten(eventId: Int)
    fun isListened(eventId: Int): Boolean
}