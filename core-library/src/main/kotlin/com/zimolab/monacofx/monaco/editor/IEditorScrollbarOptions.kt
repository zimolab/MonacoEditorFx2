package com.zimolab.monacofx.monaco.editor

open class IEditorScrollbarOptions (
    open val arrowSize: Int? = null,
    open val vertical: String? = null /* "auto" | "visible" | "hidden" */,
    open val horizontal: String? = null /* "auto" | "visible" | "hidden" */,
    open val useShadows: Boolean? = null,
    open val verticalHasArrows: Boolean? = null,
    open val horizontalHasArrows: Boolean? = null,
    open val handleMouseWheel: Boolean? = null,
    open val alwaysConsumeMouseWheel: Boolean? = null,
    open val horizontalScrollbarSize: Int? = null,
    open val verticalScrollbarSize: Int? = null,
    open val verticalSliderSize: Int? = null,
    open val horizontalSliderSize: Int? = null,
    open val scrollByPage: Boolean? = null,
)