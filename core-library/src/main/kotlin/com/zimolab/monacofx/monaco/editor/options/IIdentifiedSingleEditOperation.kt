package com.zimolab.monacofx.monaco.editor.options

import com.zimolab.monacofx.monaco.IRange

interface IIdentifiedSingleEditOperation {
    val range: IRange
    val text: String?
    val forceMoveMarkers: Boolean?
}

