package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IContentSizeChangedEvent".It may
 * be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-02T01:28:50.185858600
 */
public abstract class AbstractIContentSizeChangedEvent(
  public val targetObject: JSObject
) : IContentSizeChangedEvent {
  public override val contentHeight: Int
    get() {
      val result = targetObject.getMember("contentHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentHeight' not exists in underlying js object")
      return result as Int
    }

  public override val contentHeightChanged: Boolean
    get() {
      val result = targetObject.getMember("contentHeightChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentHeightChanged' not exists in underlying js object")
      return result as Boolean
    }

  public override val contentWidth: Int
    get() {
      val result = targetObject.getMember("contentWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentWidth' not exists in underlying js object")
      return result as Int
    }

  public override val contentWidthChanged: Boolean
    get() {
      val result = targetObject.getMember("contentWidthChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentWidthChanged' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
