package com.zimolab.monacofx.monaco.editor.options

open class IEditorFindOptions (
    open val cursorMoveOnType: Boolean? = null,
    open val seedSearchStringFromSelection: Boolean? = null,
    open val autoFindInSelection: String? = null /* "never" | "always" | "multiline" */,
    open val addExtraSpaceOnTop: Boolean? = null,
    open val loop: Boolean? = null,
)