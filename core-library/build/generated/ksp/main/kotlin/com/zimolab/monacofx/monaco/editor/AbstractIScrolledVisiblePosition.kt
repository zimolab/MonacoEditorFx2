package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.IScrolledVisiblePosition".It may be overwritten at any time,
 * every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-04T23:38:07.357689200
 */
public abstract class AbstractIScrolledVisiblePosition(
  public override val targetObject: JSObject
) : IScrolledVisiblePosition, JsInterfaceObject {
  public override val top: Int
    get() {
      val result = targetObject.getMember("top")
      if(result == "undefined" || result == null)
          throw RuntimeException("'top' not exists in underlying js object")
      return result as Int
    }

  public override val left: Int
    get() {
      val result = targetObject.getMember("left")
      if(result == "undefined" || result == null)
          throw RuntimeException("'left' not exists in underlying js object")
      return result as Int
    }

  public override val height: Int
    get() {
      val result = targetObject.getMember("height")
      if(result == "undefined" || result == null)
          throw RuntimeException("'height' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IScrolledVisiblePosition> new(
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
