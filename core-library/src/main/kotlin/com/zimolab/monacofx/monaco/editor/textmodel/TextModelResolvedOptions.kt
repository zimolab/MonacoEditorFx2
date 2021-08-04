package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.editor.options.AbstractITextModelResolvedOptions
import netscape.javascript.JSObject

class TextModelResolvedOptions(jsObject: JSObject): AbstractITextModelResolvedOptions(jsObject) {
    override val _textModelResolvedOptionsBrand: Unit
        get() = Unit
}