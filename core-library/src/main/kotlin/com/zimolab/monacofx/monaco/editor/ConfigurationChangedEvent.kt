package com.zimolab.monacofx.monaco.editor

interface ConfigurationChangedEvent {
    fun hasChanged(id: Int): Boolean
}