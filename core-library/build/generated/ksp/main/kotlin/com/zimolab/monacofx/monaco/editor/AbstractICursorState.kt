package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.ICursorState".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T02:37:57.206564200
 */
public abstract class AbstractICursorState(
  public override val targetObject: JSObject
) : ICursorState, JsInterfaceObject {
  public override val inSelectionMode: Boolean
    get() {
      val result = targetObject.getMember("inSelectionMode")
      if(result == "undefined" || result == null)
          throw RuntimeException("'inSelectionMode' not exists in underlying js object")
      return result as Boolean
    }

  public override val selectionStart: Any
    get() {
      val result = targetObject.getMember("selectionStart")
      if(result == "undefined" || result == null)
          throw RuntimeException("'selectionStart' not exists in underlying js object")
      return result as Any
    }

  public override val position: Any
    get() {
      val result = targetObject.getMember("position")
      if(result == "undefined" || result == null)
          throw RuntimeException("'position' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
