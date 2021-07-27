package com.zimolab.monacofx.jseditor

abstract class ICommandHandler(val id: String) {
    abstract fun onCommand(vararg args: Any?): Any?
}