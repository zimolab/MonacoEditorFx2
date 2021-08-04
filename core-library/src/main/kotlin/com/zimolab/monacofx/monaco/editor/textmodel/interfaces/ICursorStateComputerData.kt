package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

import com.zimolab.monacofx.monaco.Selection

interface ICursorStateComputerData {
    fun getInverseEditOperations(): Array<IValidEditOperation>
    fun getTrackedSelection(id: String): Selection
}