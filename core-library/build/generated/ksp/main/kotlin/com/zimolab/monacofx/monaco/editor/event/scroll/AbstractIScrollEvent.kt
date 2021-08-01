package com.zimolab.monacofx.monaco.editor.event.scroll

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.scroll.IScrollEvent".It may be overwritten at any time,
 * every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-02T01:28:50.208859100
 */
public abstract class AbstractIScrollEvent(
  public val targetObject: JSObject
) : IScrollEvent {
  public override val scrollTop: Int
    get() {
      val result = targetObject.getMember("scrollTop")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollTop' not exists in underlying js object")
      return result as Int
    }

  public override val scrollLeft: Int
    get() {
      val result = targetObject.getMember("scrollLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollLeft' not exists in underlying js object")
      return result as Int
    }

  public override val scrollWidth: Int
    get() {
      val result = targetObject.getMember("scrollWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollWidth' not exists in underlying js object")
      return result as Int
    }

  public override val scrollHeight: Int
    get() {
      val result = targetObject.getMember("scrollHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollHeight' not exists in underlying js object")
      return result as Int
    }

  public override val scrollTopChanged: Boolean
    get() {
      val result = targetObject.getMember("scrollTopChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollTopChanged' not exists in underlying js object")
      return result as Boolean
    }

  public override val scrollLeftChanged: Boolean
    get() {
      val result = targetObject.getMember("scrollLeftChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollLeftChanged' not exists in underlying js object")
      return result as Boolean
    }

  public override val scrollWidthChanged: Boolean
    get() {
      val result = targetObject.getMember("scrollWidthChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollWidthChanged' not exists in underlying js object")
      return result as Boolean
    }

  public override val scrollHeightChanged: Boolean
    get() {
      val result = targetObject.getMember("scrollHeightChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollHeightChanged' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
