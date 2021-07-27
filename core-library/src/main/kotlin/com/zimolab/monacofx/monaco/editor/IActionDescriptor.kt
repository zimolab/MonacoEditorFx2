package com.zimolab.monacofx.monaco.editor

import com.alibaba.fastjson.annotation.JSONField
import com.zimolab.monacofx.monaco.IDisposable

abstract class IActionDescriptor(
    @JSONField(serialize = false)
    private var editor: MonacoEditor? = null,
    val id: String,
    val label: String,
    val precondition: String? = null,
    val keybindings: Array<Int>? = null,
    val keybindingContext: String? = null,
    val contextMenuGroupId: String? = null,
    val contextMenuOrder: Float? = null,
): IDisposable {
    abstract fun onRun(actionId: String)

    fun setEditor(editor: MonacoEditor?) {
        this.editor = editor
    }
    override fun dispose(): Boolean {
        return editor?.removeAction(id) == true
    }
}