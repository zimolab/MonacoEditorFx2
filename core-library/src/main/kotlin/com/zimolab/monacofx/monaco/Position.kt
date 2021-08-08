package com.zimolab.monacofx.monaco

import netscape.javascript.JSObject

class Position(jsObject: JSObject): AbstractIPosition(jsObject) {
    override fun toString(): String {
        return "Position@($column, $lineNumber)"
    }
}