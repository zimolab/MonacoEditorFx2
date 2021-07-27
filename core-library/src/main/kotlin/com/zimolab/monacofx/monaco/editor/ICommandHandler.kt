package com.zimolab.monacofx.monaco.editor

abstract class ICommandHandler(val id: String) {
    abstract fun onCommand(vararg args: Any?): Any?
}