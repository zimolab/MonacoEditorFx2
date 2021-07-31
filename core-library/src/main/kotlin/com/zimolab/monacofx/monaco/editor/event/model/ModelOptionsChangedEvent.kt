package com.zimolab.monacofx.monaco.editor.event.model

import com.zimolab.monacofx.monaco.editor.event.model.interfaces.AbstractIModelOptionsChangedEvent
import netscape.javascript.JSObject

class ModelOptionsChangedEvent(jsObject: JSObject): AbstractIModelOptionsChangedEvent(jsObject)