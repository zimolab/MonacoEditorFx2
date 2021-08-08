package com.zimolab.monacofx.monaco.editor.event.cursor.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.event.cursor.interfaces.ICursorPositionChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-09T01:36:24.691545800
 */
public abstract class AbstractICursorPositionChangedEvent(
  public override val targetObject: JSObject
) : ICursorPositionChangedEvent, JsInterfaceObject {
  public override val position: Any
    get() {
      val result = targetObject.getMember("position")
      if(result == "undefined" || result == null)
          throw RuntimeException("'position' not exists in underlying js object")
      return result as Any
    }

  public override val secondaryPositions: Any
    get() {
      val result = targetObject.getMember("secondaryPositions")
      if(result == "undefined" || result == null)
          throw RuntimeException("'secondaryPositions' not exists in underlying js object")
      return result as Any
    }

  public override val reason: Int
    get() {
      val result = targetObject.getMember("reason")
      if(result == "undefined" || result == null)
          throw RuntimeException("'reason' not exists in underlying js object")
      return result as Int
    }

  public override val source: String
    get() {
      val result = targetObject.getMember("source")
      if(result == "undefined" || result == null)
          throw RuntimeException("'source' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : ICursorPositionChangedEvent> new(
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
