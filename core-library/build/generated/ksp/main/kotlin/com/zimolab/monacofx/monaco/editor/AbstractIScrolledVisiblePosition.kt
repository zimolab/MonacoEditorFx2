package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.IScrolledVisiblePosition".It may be overwritten at any time,
 * every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-10T02:37:57.237581300
 */
public abstract class AbstractIScrolledVisiblePosition(
  public override val targetObject: JSObject
) : IScrolledVisiblePosition, JsInterfaceObject {
  public override val top: Int
    get() {
      val result = targetObject.getMember("top")
      if(result == "undefined" || result == null)
          throw RuntimeException("'top' not exists in underlying js object")
      return result as Int
    }

  public override val left: Int
    get() {
      val result = targetObject.getMember("left")
      if(result == "undefined" || result == null)
          throw RuntimeException("'left' not exists in underlying js object")
      return result as Int
    }

  public override val height: Int
    get() {
      val result = targetObject.getMember("height")
      if(result == "undefined" || result == null)
          throw RuntimeException("'height' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
