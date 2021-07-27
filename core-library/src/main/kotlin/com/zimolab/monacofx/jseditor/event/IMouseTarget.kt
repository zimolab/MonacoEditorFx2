package com.zimolab.monacofx.jseditor.event

import com.zimolab.monacofx.jseditor.Position
import com.zimolab.monacofx.jseditor.Range

open class IMouseTarget(
    open val element: String?,
    open val type: Int? = null,
    open val position: Position?,
    open val mouseColumn: Int? = null,
    open val range: Range?,
    open val detail: String? = null
)
