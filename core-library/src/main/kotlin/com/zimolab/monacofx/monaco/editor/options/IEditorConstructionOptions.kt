package com.zimolab.monacofx.monaco.editor.options

import com.zimolab.monacofx.monaco.editor.IDimension

open class IEditorConstructionOptions(
    open val dimension: IDimension? = null,
): IEditorOptions()