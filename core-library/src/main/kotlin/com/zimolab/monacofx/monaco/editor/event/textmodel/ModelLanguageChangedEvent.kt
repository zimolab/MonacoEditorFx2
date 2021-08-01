package com.zimolab.monacofx.monaco.editor.event.textmodel

import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.AbstractIModelLanguageChangedEvent
import netscape.javascript.JSObject

class ModelLanguageChangedEvent(jsObject: JSObject): AbstractIModelLanguageChangedEvent(jsObject) {
}