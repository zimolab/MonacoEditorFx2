package com.zimolab.monacofx.jseditor

class IIdentifiedSingleEditOperation(
    var range: IRange,
    var text: String?,
    var forceMoveMarkers: Boolean?
)