package com.zimolab.monacofx.monaco.editor.event.mouse.interfaces

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.mouse.interfaces.IMouseEvent".It may be overwritten at any
 * time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-10T01:53:24.327831600
 */
public abstract class AbstractIMouseEvent(
  public override val targetObject: JSObject
) : IMouseEvent, JsInterfaceObject {
  public override val browserEvent: Any
    get() {
      val result = targetObject.getMember("browserEvent")
      if(result == "undefined" || result == null)
          throw RuntimeException("'browserEvent' not exists in underlying js object")
      return result as Any
    }

  public override val leftButton: Boolean
    get() {
      val result = targetObject.getMember("leftButton")
      if(result == "undefined" || result == null)
          throw RuntimeException("'leftButton' not exists in underlying js object")
      return result as Boolean
    }

  public override val middleButton: Boolean
    get() {
      val result = targetObject.getMember("middleButton")
      if(result == "undefined" || result == null)
          throw RuntimeException("'middleButton' not exists in underlying js object")
      return result as Boolean
    }

  public override val rightButton: Boolean
    get() {
      val result = targetObject.getMember("rightButton")
      if(result == "undefined" || result == null)
          throw RuntimeException("'rightButton' not exists in underlying js object")
      return result as Boolean
    }

  public override val buttons: Int
    get() {
      val result = targetObject.getMember("buttons")
      if(result == "undefined" || result == null)
          throw RuntimeException("'buttons' not exists in underlying js object")
      return result as Int
    }

  public override val target: Any
    get() {
      val result = targetObject.getMember("target")
      if(result == "undefined" || result == null)
          throw RuntimeException("'target' not exists in underlying js object")
      return result as Any
    }

  public override val detail: Int
    get() {
      val result = targetObject.getMember("detail")
      if(result == "undefined" || result == null)
          throw RuntimeException("'detail' not exists in underlying js object")
      return result as Int
    }

  public override val posx: Int
    get() {
      val result = targetObject.getMember("posx")
      if(result == "undefined" || result == null)
          throw RuntimeException("'posx' not exists in underlying js object")
      return result as Int
    }

  public override val posy: Int
    get() {
      val result = targetObject.getMember("posy")
      if(result == "undefined" || result == null)
          throw RuntimeException("'posy' not exists in underlying js object")
      return result as Int
    }

  public override val ctrlKey: Boolean
    get() {
      val result = targetObject.getMember("ctrlKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'ctrlKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val shiftKey: Boolean
    get() {
      val result = targetObject.getMember("shiftKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'shiftKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val altKey: Boolean
    get() {
      val result = targetObject.getMember("altKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'altKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val metaKey: Boolean
    get() {
      val result = targetObject.getMember("metaKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'metaKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val timestamp: Double
    get() {
      val result = targetObject.getMember("timestamp")
      if(result == "undefined" || result == null)
          throw RuntimeException("'timestamp' not exists in underlying js object")
      return result as Double
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun preventDefault(): Unit {
    val result = targetObject.call("preventDefault", )
    if(result == "undefined" || result !is Unit)
        throw RuntimeException("return value type is not as expected")
    return result as Unit
  }

  public override fun stopPropagation(): Unit {
    val result = targetObject.call("stopPropagation", )
    if(result == "undefined" || result !is Unit)
        throw RuntimeException("return value type is not as expected")
    return result as Unit
  }

  public companion object {
    public inline fun <reified T : IMouseEvent> new(
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
