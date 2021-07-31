package com.zimolab.monacofx.monaco.editor.event.miscellaneous

import com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.AbstractIConfigurationChangedEvent
import netscape.javascript.JSObject

class ConfigurationChangedEvent(jsObject: JSObject): AbstractIConfigurationChangedEvent(jsObject)