package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IOverviewRulerPosition".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-07-31T15:28:44.056925500
 */
public abstract class AbstractIOverviewRulerPosition(
  public val targetObject: JSObject
) : IOverviewRulerPosition {
  public override val width: Int
    get() {
      val result = targetObject.getMember("width")
      if(result == "undefined" || result == null)
          throw RuntimeException("'width' not exists in underlying js object")
      return result as Int
    }

  public override val height: Int
    get() {
      val result = targetObject.getMember("height")
      if(result == "undefined" || result == null)
          throw RuntimeException("'height' not exists in underlying js object")
      return result as Int
    }

  public override val top: Int
    get() {
      val result = targetObject.getMember("top")
      if(result == "undefined" || result == null)
          throw RuntimeException("'top' not exists in underlying js object")
      return result as Int
    }

  public override val right: Int
    get() {
      val result = targetObject.getMember("right")
      if(result == "undefined" || result == null)
          throw RuntimeException("'right' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
