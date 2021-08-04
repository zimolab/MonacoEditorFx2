package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IOverviewRulerPosition".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-04T23:38:07.431705100
 */
public abstract class AbstractIOverviewRulerPosition(
  public override val targetObject: JSObject
) : IOverviewRulerPosition, JsInterfaceObject {
  public override val width: Int
    get() {
      val result = targetObject.getMember("width")
      if(result == "undefined" || result == null)
          throw RuntimeException("'width' not exists in underlying js object")
      return result as Int
    }

  public override val height: Int
    get() {
      val result = targetObject.getMember("height")
      if(result == "undefined" || result == null)
          throw RuntimeException("'height' not exists in underlying js object")
      return result as Int
    }

  public override val top: Int
    get() {
      val result = targetObject.getMember("top")
      if(result == "undefined" || result == null)
          throw RuntimeException("'top' not exists in underlying js object")
      return result as Int
    }

  public override val right: Int
    get() {
      val result = targetObject.getMember("right")
      if(result == "undefined" || result == null)
          throw RuntimeException("'right' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IOverviewRulerPosition> new(
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
