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
 * This class is auto-generated from "com.zimolab.monacofx.monaco.ISelection".It may be overwritten
 * at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-10T01:53:24.147792700
 */
public abstract class AbstractISelection(
  public override val targetObject: JSObject
) : ISelection, JsInterfaceObject {
  public override val selectionStartLineNumber: Int
    get() {
      val result = targetObject.getMember("selectionStartLineNumber")
      if(result == "undefined" || result == null)
          throw RuntimeException("'selectionStartLineNumber' not exists in underlying js object")
      return result as Int
    }

  public override val selectionStartColumn: Int
    get() {
      val result = targetObject.getMember("selectionStartColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'selectionStartColumn' not exists in underlying js object")
      return result as Int
    }

  public override val positionLineNumber: Int
    get() {
      val result = targetObject.getMember("positionLineNumber")
      if(result == "undefined" || result == null)
          throw RuntimeException("'positionLineNumber' not exists in underlying js object")
      return result as Int
    }

  public override val positionColumn: Int
    get() {
      val result = targetObject.getMember("positionColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'positionColumn' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun equalsSelection(other: Any): Boolean {
    val result = targetObject.call("equalsSelection", other)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun getDirection(): Int {
    val result = targetObject.call("getDirection", )
    if(result == "undefined" || result !is Int)
        throw RuntimeException("return value type is not as expected")
    return result as Int
  }

  public override fun setEndPosition(endLineNumber: Int, endColumn: Int): Any {
    val result = targetObject.call("setEndPosition", endLineNumber,endColumn)
    if(result == "undefined" || result !is Any)
        throw RuntimeException("return value type is not as expected")
    return result as Any
  }

  public override fun getPosition(): Any {
    val result = targetObject.call("getPosition", )
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

  public companion object {
    public inline fun <reified T : ISelection> new(
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
