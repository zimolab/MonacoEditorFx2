package com.zimolab.monacofx.jseditor.event

interface IEditorEventListener {
    fun listen(event: Int, callback: (e: Any?) -> Any?)
    fun unlisten(event: Int)
    fun isListened(event: Int): Boolean
}