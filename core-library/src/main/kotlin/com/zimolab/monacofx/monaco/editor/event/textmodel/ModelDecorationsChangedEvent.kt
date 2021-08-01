package com.zimolab.monacofx.monaco.editor.event.textmodel

import com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.AbstractIModelDecorationsChangedEvent
import netscape.javascript.JSObject

class ModelDecorationsChangedEvent(jsObject: JSObject) : AbstractIModelDecorationsChangedEvent(jsObject)