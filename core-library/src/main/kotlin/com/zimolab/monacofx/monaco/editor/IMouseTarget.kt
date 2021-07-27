package com.zimolab.monacofx.monaco.editor

import com.zimolab.monacofx.monaco.Position
import com.zimolab.monacofx.monaco.Range

open class IMouseTarget(
    open val element: String?,
    open val type: Int? = null,
    open val position: Position?,
    open val mouseColumn: Int? = null,
    open val range: Range?,
    open val detail: String? = null
)
