package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IConfigurationChangedEvent {

    @JsFunction
    fun hasChanged(editorOption: Int): Boolean
}