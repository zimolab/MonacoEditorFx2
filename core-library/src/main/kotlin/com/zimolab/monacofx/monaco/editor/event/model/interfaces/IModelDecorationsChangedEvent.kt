package com.zimolab.monacofx.monaco.editor.event.model.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IModelDecorationsChangedEvent {
    val affectsMinimap: Boolean
    val affectsOverviewRuler: Boolean
}
