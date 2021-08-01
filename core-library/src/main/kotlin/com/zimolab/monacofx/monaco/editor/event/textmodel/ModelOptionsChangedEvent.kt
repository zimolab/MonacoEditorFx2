package com.zimolab.monacofx.monaco.editor.event.textmodel

import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.AbstractIModelOptionsChangedEvent
import netscape.javascript.JSObject

class ModelOptionsChangedEvent(jsObject: JSObject): AbstractIModelOptionsChangedEvent(jsObject)