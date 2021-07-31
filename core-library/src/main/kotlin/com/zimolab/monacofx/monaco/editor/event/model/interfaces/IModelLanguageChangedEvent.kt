package com.zimolab.monacofx.monaco.editor.event.model.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IModelLanguageChangedEvent {
    val oldLanguage: String
    val newLanguage: String
}

