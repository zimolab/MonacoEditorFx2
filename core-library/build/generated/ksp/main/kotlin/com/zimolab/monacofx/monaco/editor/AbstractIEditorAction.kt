package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.IEditorAction".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T02:37:57.220568200
 */
public abstract class AbstractIEditorAction(
  public override val targetObject: JSObject
) : IEditorAction, JsInterfaceObject {
  public override val id: String
    get() {
      val result = targetObject.getMember("id")
      if(result == "undefined" || result == null)
          throw RuntimeException("'id' not exists in underlying js object")
      return result as String
    }

  public override val label: String
    get() {
      val result = targetObject.getMember("label")
      if(result == "undefined" || result == null)
          throw RuntimeException("'label' not exists in underlying js object")
      return result as String
    }

  public override val alias: String
    get() {
      val result = targetObject.getMember("alias")
      if(result == "undefined" || result == null)
          throw RuntimeException("'alias' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun isSupported(): Boolean {
    val result = targetObject.call("isSupported", )
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun run(): Any? {
    val result = targetObject.call("run", )
    if(result == "undefined" || result !is Any)
        return null
    return result as? Any
  }

  public companion object
}
