package com.zimolab.monacofx.monaco.editor.options

import com.zimolab.monacofx.monaco.IRange

class IdentifiedSingleEditOperation(
    override val range: IRange,
    override val text: String?,
    override val forceMoveMarkers: Boolean?
) : IIdentifiedSingleEditOperation {

}