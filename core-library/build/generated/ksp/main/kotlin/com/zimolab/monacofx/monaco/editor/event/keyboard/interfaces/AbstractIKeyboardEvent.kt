package com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces.IKeyboardEvent".It may be overwritten
 * at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-10T02:37:57.279573500
 */
public abstract class AbstractIKeyboardEvent(
  public override val targetObject: JSObject
) : IKeyboardEvent, JsInterfaceObject {
  public override val browserEvent: Any
    get() {
      val result = targetObject.getMember("browserEvent")
      if(result == "undefined" || result == null)
          throw RuntimeException("'browserEvent' not exists in underlying js object")
      return result as Any
    }

  public override val ctrlKey: Boolean
    get() {
      val result = targetObject.getMember("ctrlKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'ctrlKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val shiftKey: Boolean
    get() {
      val result = targetObject.getMember("shiftKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'shiftKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val altKey: Boolean
    get() {
      val result = targetObject.getMember("altKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'altKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val metaKey: Boolean
    get() {
      val result = targetObject.getMember("metaKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'metaKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val keyCode: Int
    get() {
      val result = targetObject.getMember("keyCode")
      if(result == "undefined" || result == null)
          throw RuntimeException("'keyCode' not exists in underlying js object")
      return result as Int
    }

  public override val code: String
    get() {
      val result = targetObject.getMember("code")
      if(result == "undefined" || result == null)
          throw RuntimeException("'code' not exists in underlying js object")
      return result as String
    }

  public override val target: Any
    get() {
      val result = targetObject.getMember("target")
      if(result == "undefined" || result == null)
          throw RuntimeException("'target' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun preventDefault(): Unit {
    val result = targetObject.call("preventDefault", )
    if(result == "undefined" || result !is Unit)
        throw RuntimeException("return value type is not as expected")
    return result as Unit
  }

  public override fun stopPropagation(): Unit {
    val result = targetObject.call("stopPropagation", )
    if(result == "undefined" || result !is Unit)
        throw RuntimeException("return value type is not as expected")
    return result as Unit
  }

  public companion object
}
