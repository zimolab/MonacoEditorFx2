package com.zimolab.monacofx.monaco.editor.options

import com.zimolab.monacofx.monaco.IRange

class IIdentifiedSingleEditOperation(
    var range: IRange,
    var text: String?,
    var forceMoveMarkers: Boolean?
)