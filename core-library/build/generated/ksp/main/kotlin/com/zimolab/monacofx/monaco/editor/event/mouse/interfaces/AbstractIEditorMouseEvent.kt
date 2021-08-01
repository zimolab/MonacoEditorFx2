package com.zimolab.monacofx.monaco.editor.event.mouse.interfaces

import kotlin.Any
import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.IEditorMouseEvent".It may be overwritten
 * at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-01T19:03:48.125977300
 */
public abstract class AbstractIEditorMouseEvent(
  public val targetObject: JSObject
) : IEditorMouseEvent {
  public override val event: Any
    get() {
      val result = targetObject.getMember("event")
      if(result == "undefined" || result == null)
          throw RuntimeException("'event' not exists in underlying js object")
      return result as Any
    }

  public override val target: Any
    get() {
      val result = targetObject.getMember("target")
      if(result == "undefined" || result == null)
          throw RuntimeException("'target' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
