package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

import com.zimolab.monacofx.monaco.IRange

interface IModelDeltaDecoration {
    val range: IRange
    val options: IModelDecorationOptions
}

