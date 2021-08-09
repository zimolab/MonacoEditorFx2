package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.textmodel.IFindMatch".It
 * may be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from
 * it with your own implementation.
 * @2021-08-10T02:37:57.397599
 */
public abstract class AbstractIFindMatch(
  public override val targetObject: JSObject
) : IFindMatch, JsInterfaceObject {
  public override val matches: Any?
    get() {
      var result = targetObject.getMember("matches")
      if(result == "undefined")
          result = null
      return result?.let { it as Any }
    }

  public override val range: Any
    get() {
      val result = targetObject.getMember("range")
      if(result == "undefined" || result == null)
          throw RuntimeException("'range' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
