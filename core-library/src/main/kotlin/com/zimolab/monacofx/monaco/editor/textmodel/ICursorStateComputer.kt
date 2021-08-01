package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.Selection

interface ICursorStateComputerData {
    fun getInverseEditOperations(): Array<IValidEditOperation>
    fun getTrackedSelection(id: String): Selection
}

interface ICursorStateComputer {
    operator fun invoke(inverseEditOperations: Array<IValidEditOperation>): Array<Selection>?

}
