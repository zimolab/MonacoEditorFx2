package com.zimolab.monacofx.jseditor.event.dom

import netscape.javascript.JSObject

abstract class MouseEvent private constructor(protected val jsObject: JSObject): UIEvent {

}