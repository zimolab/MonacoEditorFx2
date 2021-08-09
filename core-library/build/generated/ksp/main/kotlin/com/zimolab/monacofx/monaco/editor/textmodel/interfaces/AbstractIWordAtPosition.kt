package com.zimolab.monacofx.monaco.editor.textmodel.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.textmodel.interfaces.IWordAtPosition".It may be overwritten at
 * any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-10T01:53:24.394848800
 */
public abstract class AbstractIWordAtPosition(
  public override val targetObject: JSObject
) : IWordAtPosition, JsInterfaceObject {
  public override val word: String
    get() {
      val result = targetObject.getMember("word")
      if(result == "undefined" || result == null)
          throw RuntimeException("'word' not exists in underlying js object")
      return result as String
    }

  public override val startColumn: Int
    get() {
      val result = targetObject.getMember("startColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'startColumn' not exists in underlying js object")
      return result as Int
    }

  public override val endColumn: Int
    get() {
      val result = targetObject.getMember("endColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'endColumn' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IWordAtPosition> new(
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
