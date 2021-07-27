package com.zimolab.monacofx.monaco.editor

interface IEditorEventListener {
    fun listen(event: Int, callback: (e: Any?) -> Any?)
    fun unlisten(event: Int)
    fun isListened(event: Int): Boolean
}