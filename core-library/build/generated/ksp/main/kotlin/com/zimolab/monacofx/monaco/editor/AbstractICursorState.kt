package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.ICursorState".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T01:53:24.176798300
 */
public abstract class AbstractICursorState(
  public override val targetObject: JSObject
) : ICursorState, JsInterfaceObject {
  public override val inSelectionMode: Boolean
    get() {
      val result = targetObject.getMember("inSelectionMode")
      if(result == "undefined" || result == null)
          throw RuntimeException("'inSelectionMode' not exists in underlying js object")
      return result as Boolean
    }

  public override val selectionStart: Any
    get() {
      val result = targetObject.getMember("selectionStart")
      if(result == "undefined" || result == null)
          throw RuntimeException("'selectionStart' not exists in underlying js object")
      return result as Any
    }

  public override val position: Any
    get() {
      val result = targetObject.getMember("position")
      if(result == "undefined" || result == null)
          throw RuntimeException("'position' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : ICursorState> new(
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
