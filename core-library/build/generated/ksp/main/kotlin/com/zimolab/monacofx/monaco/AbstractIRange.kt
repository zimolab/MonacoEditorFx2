package com.zimolab.monacofx.monaco

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.IRange".It may be overwritten at
 * any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-04T23:44:29.356464500
 */
public abstract class AbstractIRange(
  public override val targetObject: JSObject
) : IRange, JsInterfaceObject {
  public override val startLineNumber: Int
    get() {
      val result = targetObject.getMember("startLineNumber")
      if(result == "undefined" || result == null)
          throw RuntimeException("'startLineNumber' not exists in underlying js object")
      return result as Int
    }

  public override val startColumn: Int
    get() {
      val result = targetObject.getMember("startColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'startColumn' not exists in underlying js object")
      return result as Int
    }

  public override val endLineNumber: Int
    get() {
      val result = targetObject.getMember("endLineNumber")
      if(result == "undefined" || result == null)
          throw RuntimeException("'endLineNumber' not exists in underlying js object")
      return result as Int
    }

  public override val endColumn: Int
    get() {
      val result = targetObject.getMember("endColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'endColumn' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun isEmpty(): Boolean {
    val result = targetObject.call("isEmpty", )
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun containsPosition(position: Any): Boolean {
    val result = targetObject.call("containsPosition", position)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun containsRange(range: Any): Boolean {
    val result = targetObject.call("containsRange", range)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun strictContainsRange(range: Any): Boolean {
    val result = targetObject.call("strictContainsRange", range)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun plusRange(range: Any): Any {
    val result = targetObject.call("plusRange", range)
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun intersectRanges(range: Any): Any? {
    val result = targetObject.call("intersectRanges", range)
    if(result == "undefined" || result !is Any)
        return null
    return result as? Any
  }

  public override fun equalsRange(other: Any?): Boolean {
    val result = targetObject.call("equalsRange", other)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun getEndPosition(): Any {
    val result = targetObject.call("getEndPosition", )
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun getStartPosition(): Any {
    val result = targetObject.call("getStartPosition", )
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun setEndPosition(endLineNumber: Int, endColumn: Int): Any {
    val result = targetObject.call("setEndPosition", endLineNumber,endColumn)
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun setStartPosition(startLineNumber: Int, startColumn: Int): Any {
    val result = targetObject.call("setStartPosition", startLineNumber,startColumn)
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun collapseToStart(): Any {
    val result = targetObject.call("collapseToStart", )
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public companion object {
    public inline fun <reified T : IRange> new(
      webEngine: WebEngine,
      jsCode: String,
      vararg args: Any
    ): T? {
      val clz = T::class
      if (clz.isAbstract)
          throw InstantiationError("abstract class can not be instantiated")
      var c:KFunction<*>? = null
      clz.constructors.forEach {
          if (it.parameters.size == (args.size + 2))
              c = it
      }
      if(c == null)
          throw InstantiationError("constructor parameters not match")
      val targetObject = webEngine.executeScript(jsCode)
      if(targetObject == "undefined" || targetObject !is JSObject)
          return null
      return c?.call(targetObject as JSObject, webEngine, *args) as? T
    }
  }
}
