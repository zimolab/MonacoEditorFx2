package com.zimolab.monacofx.monaco.editor.event.model

import com.zimolab.monacofx.monaco.editor.event.model.interfaces.AbstractIModelDecorationsChangedEvent
import netscape.javascript.JSObject

class ModelDecorationsChangedEvent(jsObject: JSObject) : AbstractIModelDecorationsChangedEvent(jsObject)