package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.textmodel.IFindMatch".It
 * may be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from
 * it with your own implementation.
 * @2021-08-10T01:53:24.387846
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

  public companion object {
    public inline fun <reified T : IFindMatch> new(
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
