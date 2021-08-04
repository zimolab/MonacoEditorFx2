package com.zimolab.monacofx.monaco.editor.event.mouse.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.IMouseTarget".It may be overwritten at
 * any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-04T23:38:07.490720400
 */
public abstract class AbstractIMouseTarget(
  public override val targetObject: JSObject
) : IMouseTarget, JsInterfaceObject {
  public override val type: Int
    get() {
      val result = targetObject.getMember("type")
      if(result == "undefined" || result == null)
          throw RuntimeException("'type' not exists in underlying js object")
      return result as Int
    }

  public override val position: Any
    get() {
      val result = targetObject.getMember("position")
      if(result == "undefined" || result == null)
          throw RuntimeException("'position' not exists in underlying js object")
      return result as Any
    }

  public override val mouseColumn: Int
    get() {
      val result = targetObject.getMember("mouseColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'mouseColumn' not exists in underlying js object")
      return result as Int
    }

  public override val range: Any
    get() {
      val result = targetObject.getMember("range")
      if(result == "undefined" || result == null)
          throw RuntimeException("'range' not exists in underlying js object")
      return result as Any
    }

  public override val detail: Any
    get() {
      val result = targetObject.getMember("detail")
      if(result == "undefined" || result == null)
          throw RuntimeException("'detail' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IMouseTarget> new(
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
