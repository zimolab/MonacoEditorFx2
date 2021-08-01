package com.zimolab.monacofx.monaco.editor.options

interface IEditorEventProcessor {
    fun listen(eventId: Int, callback: (eventId: Int, e: Any?) -> Any?)
    fun unlisten(eventId: Int)
    fun isListened(eventId: Int): Boolean
}