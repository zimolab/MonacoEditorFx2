package com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelOptionsChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T01:53:24.374844200
 */
public abstract class AbstractIModelOptionsChangedEvent(
  public override val targetObject: JSObject
) : IModelOptionsChangedEvent, JsInterfaceObject {
  public override val tabSize: Boolean
    get() {
      val result = targetObject.getMember("tabSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'tabSize' not exists in underlying js object")
      return result as Boolean
    }

  public override val indentSize: Boolean
    get() {
      val result = targetObject.getMember("indentSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'indentSize' not exists in underlying js object")
      return result as Boolean
    }

  public override val insertSpaces: Boolean
    get() {
      val result = targetObject.getMember("insertSpaces")
      if(result == "undefined" || result == null)
          throw RuntimeException("'insertSpaces' not exists in underlying js object")
      return result as Boolean
    }

  public override val trimAutoWhitespace: Boolean
    get() {
      val result = targetObject.getMember("trimAutoWhitespace")
      if(result == "undefined" || result == null)
          throw RuntimeException("'trimAutoWhitespace' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IModelOptionsChangedEvent> new(
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
