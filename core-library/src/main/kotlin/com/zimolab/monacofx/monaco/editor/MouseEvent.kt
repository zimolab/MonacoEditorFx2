package com.zimolab.monacofx.monaco.editor

import netscape.javascript.JSObject

abstract class MouseEvent private constructor(protected val jsObject: JSObject): UIEvent {

}