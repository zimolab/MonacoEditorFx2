package com.zimolab.monacofx.monaco.editor.options

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.options.ITextModelResolvedOptions".It may be overwritten at any
 * time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-10T02:37:57.390597400
 */
public abstract class AbstractITextModelResolvedOptions(
  public override val targetObject: JSObject
) : ITextModelResolvedOptions, JsInterfaceObject {
  public override val tabSize: Int
    get() {
      val result = targetObject.getMember("tabSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'tabSize' not exists in underlying js object")
      return result as Int
    }

  public override val indentSize: Int
    get() {
      val result = targetObject.getMember("indentSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'indentSize' not exists in underlying js object")
      return result as Int
    }

  public override val insertSpaces: Boolean
    get() {
      val result = targetObject.getMember("insertSpaces")
      if(result == "undefined" || result == null)
          throw RuntimeException("'insertSpaces' not exists in underlying js object")
      return result as Boolean
    }

  public override val defaultEOL: Int
    get() {
      val result = targetObject.getMember("defaultEOL")
      if(result == "undefined" || result == null)
          throw RuntimeException("'defaultEOL' not exists in underlying js object")
      return result as Int
    }

  public override val trimAutoWhitespace: Boolean
    get() {
      val result = targetObject.getMember("trimAutoWhitespace")
      if(result == "undefined" || result == null)
          throw RuntimeException("'trimAutoWhitespace' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
