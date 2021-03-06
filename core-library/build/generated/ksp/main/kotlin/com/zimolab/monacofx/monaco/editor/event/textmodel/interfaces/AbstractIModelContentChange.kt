package com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelContentChange".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T02:37:57.360591700
 */
public abstract class AbstractIModelContentChange(
  public override val targetObject: JSObject
) : IModelContentChange, JsInterfaceObject {
  public override val range: Any
    get() {
      val result = targetObject.getMember("range")
      if(result == "undefined" || result == null)
          throw RuntimeException("'range' not exists in underlying js object")
      return result as Any
    }

  public override val rangeOffset: Int
    get() {
      val result = targetObject.getMember("rangeOffset")
      if(result == "undefined" || result == null)
          throw RuntimeException("'rangeOffset' not exists in underlying js object")
      return result as Int
    }

  public override val rangeLength: Int
    get() {
      val result = targetObject.getMember("rangeLength")
      if(result == "undefined" || result == null)
          throw RuntimeException("'rangeLength' not exists in underlying js object")
      return result as Int
    }

  public override val text: String
    get() {
      val result = targetObject.getMember("text")
      if(result == "undefined" || result == null)
          throw RuntimeException("'text' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
