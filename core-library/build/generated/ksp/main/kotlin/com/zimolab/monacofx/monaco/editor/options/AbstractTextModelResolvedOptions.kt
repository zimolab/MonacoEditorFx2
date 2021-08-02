package com.zimolab.monacofx.monaco.editor.options

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
 * "com.zimolab.monacofx.monaco.editor.options.TextModelResolvedOptions".It may be overwritten at any
 * time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it with your own
 * implementation.
 * @2021-08-02T11:32:17.605269900
 */
public abstract class AbstractTextModelResolvedOptions(
  public override val targetObject: JSObject
) : TextModelResolvedOptions, JsInterfaceObject {
  public override val tabSize: Int
    get() {
      val result = targetObject.getMember("tabSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'tabSize' not exists in underlying js object")
      return result as Int
    }

  public override val indentSize: Int
    get() {
      val result = targetObject.getMember("indentSize")
      if(result == "undefined" || result == null)
          throw RuntimeException("'indentSize' not exists in underlying js object")
      return result as Int
    }

  public override val insertSpaces: Boolean
    get() {
      val result = targetObject.getMember("insertSpaces")
      if(result == "undefined" || result == null)
          throw RuntimeException("'insertSpaces' not exists in underlying js object")
      return result as Boolean
    }

  public override val defaultEOL: Int
    get() {
      val result = targetObject.getMember("defaultEOL")
      if(result == "undefined" || result == null)
          throw RuntimeException("'defaultEOL' not exists in underlying js object")
      return result as Int
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
    public inline fun <reified T : TextModelResolvedOptions> new(
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
