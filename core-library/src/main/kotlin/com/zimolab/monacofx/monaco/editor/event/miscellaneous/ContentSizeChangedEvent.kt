package com.zimolab.monacofx.monaco.editor.event.miscellaneous

import com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.AbstractIContentSizeChangedEvent
import netscape.javascript.JSObject

class ContentSizeChangedEvent(jsObject: JSObject): AbstractIContentSizeChangedEvent(jsObject)