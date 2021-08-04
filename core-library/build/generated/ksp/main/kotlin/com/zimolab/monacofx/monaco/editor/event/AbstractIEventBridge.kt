package com.zimolab.monacofx.monaco.editor.event

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.event.IEventBridge".It may
 * be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-04T23:38:07.365707700
 */
public abstract class AbstractIEventBridge(
  public override val targetObject: JSObject
) : IEventBridge, JsInterfaceObject {
  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun listen(eventId: Int): Boolean {
    val result = targetObject.call("listen", eventId)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun unlisten(eventId: Int): Boolean {
    val result = targetObject.call("unlisten", eventId)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun isListened(eventId: Int): Boolean {
    val result = targetObject.call("isListened", eventId)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public companion object {
    public inline fun <reified T : IEventBridge> new(
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
