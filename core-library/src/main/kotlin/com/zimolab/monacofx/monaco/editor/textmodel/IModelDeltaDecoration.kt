package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.IRange

interface IModelDeltaDecoration {
    val range: IRange
    val options: IModelDecorationOptions
}

