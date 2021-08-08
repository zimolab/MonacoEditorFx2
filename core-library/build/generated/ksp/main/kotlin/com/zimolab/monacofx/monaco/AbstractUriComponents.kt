package com.zimolab.monacofx.monaco

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.UriComponents".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-09T01:15:59.234727900
 */
public abstract class AbstractUriComponents(
  public override val targetObject: JSObject
) : UriComponents, JsInterfaceObject {
  public override val authority: String?
    get() {
      val result = targetObject.getMember("authority")
      if(result == "undefined" || result == null)
          throw RuntimeException("'authority' not exists in underlying js object")
      return result as String
    }

  public override val fragment: String?
    get() {
      val result = targetObject.getMember("fragment")
      if(result == "undefined" || result == null)
          throw RuntimeException("'fragment' not exists in underlying js object")
      return result as String
    }

  public override val path: String?
    get() {
      val result = targetObject.getMember("path")
      if(result == "undefined" || result == null)
          throw RuntimeException("'path' not exists in underlying js object")
      return result as String
    }

  public override val query: String?
    get() {
      val result = targetObject.getMember("query")
      if(result == "undefined" || result == null)
          throw RuntimeException("'query' not exists in underlying js object")
      return result as String
    }

  public override val scheme: String?
    get() {
      val result = targetObject.getMember("scheme")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scheme' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : UriComponents> new(
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
