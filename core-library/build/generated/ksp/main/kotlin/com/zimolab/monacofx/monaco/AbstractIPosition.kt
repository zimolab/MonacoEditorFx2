package com.zimolab.monacofx.monaco

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.IPosition".It may be overwritten
 * at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-01T23:11:49.219354800
 */
public abstract class AbstractIPosition(
  public val targetObject: JSObject
) : IPosition {
  public override val column: Int
    get() {
      val result = targetObject.getMember("column")
      if(result == "undefined" || result == null)
          throw RuntimeException("'column' not exists in underlying js object")
      return result as Int
    }

  public override val lineNumber: Int
    get() {
      val result = targetObject.getMember("lineNumber")
      if(result == "undefined" || result == null)
          throw RuntimeException("'lineNumber' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun with(newLineNumber: Int, newColumn: Int): Any {
    val result = targetObject.call("with", newLineNumber,newColumn)
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun delta(deltaLineNumber: Int, deltaColumn: Int): Any {
    val result = targetObject.call("delta", deltaLineNumber,deltaColumn)
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun isBefore(other: Any): Boolean {
    val result = targetObject.call("isBefore", other)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun isBeforeOrEqual(other: Any): Boolean {
    val result = targetObject.call("isBeforeOrEqual", other)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public companion object
}
