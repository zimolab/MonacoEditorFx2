package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.editor.event.textmodel.ModelContentChangedEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelContentChangedEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelDecorationsChangedEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelLanguageChangedEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelOptionsChangedEvent
import netscape.javascript.JSObject

typealias TextModelEventListener = (eventId: Int, e: Any?) -> Any?
typealias ModelContentChangedListener = ((eventId: Int, event: ModelContentChangedEvent)->Unit)
typealias ModelDecorationsChangedListener = (eventId: Int, e: IModelDecorationsChangedEvent) -> Unit
typealias ModelOptionsChangedListener = (eventId: Int, event: IModelOptionsChangedEvent) -> Unit
typealias ModelLanguageChangedListener =  (eventId: Int, event: IModelLanguageChangedEvent) -> Unit
typealias ModelChangeLanguageConfigurationListener =  (eventId: Int, e: JSObject) -> Unit
typealias ModelChangeAttachedListener =  (eventId: Int) -> Unit
typealias ModelWillDisposeListener =  (eventId: Int) -> Unit