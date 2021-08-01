package com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces

import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelOptionsChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-01T23:11:49.496416500
 */
public abstract class AbstractIModelOptionsChangedEvent(
  public val targetObject: JSObject
) : IModelOptionsChangedEvent {
  public override val tabSize: Boolean
    get() {
      val result = targetObject.getMember("tabSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'tabSize' not exists in underlying js object")
      return result as Boolean
    }

  public override val indentSize: Boolean
    get() {
      val result = targetObject.getMember("indentSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'indentSize' not exists in underlying js object")
      return result as Boolean
    }

  public override val insertSpaces: Boolean
    get() {
      val result = targetObject.getMember("insertSpaces")
      if(result == "undefined" || result == null)
          throw RuntimeException("'insertSpaces' not exists in underlying js object")
      return result as Boolean
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
