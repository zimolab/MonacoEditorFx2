package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.monaco.Range
import netscape.javascript.JSObject

@JsInterface
interface IFindMatch {
    val _findMatchBrand: Unit
    @JsField(ignoreUndefined = true)
    val matches: Any?
    val range: Any
}

class FindMatch(jsObject: JSObject): AbstractIFindMatch(jsObject) {
    override val _findMatchBrand: Unit
        get() = Unit

    private val _range by lazy {
        Range(super.range as JSObject)
    }

    private val _matches by lazy {
        val r = super.matches
        if (r is JSObject)
            JsArray(r)
        else
            null
    }

    override val matches: JsArray?
        get() = _matches
    override val range: Range
        get() = _range
}