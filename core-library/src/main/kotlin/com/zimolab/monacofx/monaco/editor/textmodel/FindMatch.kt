package com.zimolab.monacofx.monaco.editor.textmodel

import netscape.javascript.JSObject

interface FindMatch {
    val _findMatchBrand: Unit
    val matches: JSObject
    val range: JSObject
}