package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import kotlin.Any
import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IPasteEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-07-31T22:39:44.363407700
 */
public abstract class AbstractIPasteEvent(
  public val targetObject: JSObject
) : IPasteEvent {
  public override val range: Any
    get() {
      val result = targetObject.getMember("range")
      if(result == "undefined" || result == null)
          throw RuntimeException("'range' not exists in underlying js object")
      return result as Any
    }

  public override val mode: String?
    get() {
      var result = targetObject.getMember("mode")
      if(result == "undefined")
          result = null
      return result?.let { it as String }
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
