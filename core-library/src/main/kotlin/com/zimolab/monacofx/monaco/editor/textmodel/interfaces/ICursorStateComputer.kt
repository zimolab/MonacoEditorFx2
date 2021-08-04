package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

import com.zimolab.monacofx.monaco.Selection

interface ICursorStateComputer {
    operator fun invoke(inverseEditOperations: Array<IValidEditOperation>): Array<Selection>?

}
