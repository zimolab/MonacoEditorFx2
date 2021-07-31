package com.zimolab.monacofx.monaco.editor.event.model

import com.zimolab.monacofx.monaco.editor.event.model.interfaces.AbstractIModelLanguageChangedEvent
import netscape.javascript.JSObject

class ModelLanguageChangedEvent(jsObject: JSObject): AbstractIModelLanguageChangedEvent(jsObject) {
}