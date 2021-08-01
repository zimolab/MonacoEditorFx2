package com.zimolab.monacofx.monaco.editor.event.cursor.interfaces

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.cursor.interfaces.ICursorPositionChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-01T19:03:47.995945800
 */
public abstract class AbstractICursorPositionChangedEvent(
  public val targetObject: JSObject
) : ICursorPositionChangedEvent {
  public override val position: Any
    get() {
      val result = targetObject.getMember("position")
      if(result == "undefined" || result == null)
          throw RuntimeException("'position' not exists in underlying js object")
      return result as Any
    }

  public override val secondaryPositions: Any
    get() {
      val result = targetObject.getMember("secondaryPositions")
      if(result == "undefined" || result == null)
          throw RuntimeException("'secondaryPositions' not exists in underlying js object")
      return result as Any
    }

  public override val reason: Int
    get() {
      val result = targetObject.getMember("reason")
      if(result == "undefined" || result == null)
          throw RuntimeException("'reason' not exists in underlying js object")
      return result as Int
    }

  public override val source: String
    get() {
      val result = targetObject.getMember("source")
      if(result == "undefined" || result == null)
          throw RuntimeException("'source' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
