package com.zimolab.monacofx.monaco.editor

open class IEditorMinimapOptions (
    open val enabled: Boolean? = null,
    open val side: String? = null /* "right" | "left" */,
    open val size: String? = null /* "proportional" | "fill" | "fit" */,
    open val showSlider: String? = null /* "always" | "mouseover" */,
    open val renderCharacters: Boolean? = null,
    open val maxColumn: Int? = null,
    open val scale: Float? = null,
)