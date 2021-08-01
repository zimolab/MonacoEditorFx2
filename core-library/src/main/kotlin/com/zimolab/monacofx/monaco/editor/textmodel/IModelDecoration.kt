package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.Range

interface IModelDecoration {
    var id: String
    var ownerId: Number
    var range: Range
    var options: IModelDecorationOptions
}

