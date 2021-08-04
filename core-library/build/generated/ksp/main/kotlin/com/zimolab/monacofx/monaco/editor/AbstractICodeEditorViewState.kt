package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.ICodeEditorViewState".It
 * may be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from
 * it with your own implementation.
 * @2021-08-04T23:44:29.396458400
 */
public abstract class AbstractICodeEditorViewState(
  public override val targetObject: JSObject
) : ICodeEditorViewState, JsInterfaceObject {
  public override val cursorState: Any
    get() {
      val result = targetObject.getMember("cursorState")
      if(result == "undefined" || result == null)
          throw RuntimeException("'cursorState' not exists in underlying js object")
      return result as Any
    }

  public override val viewState: Any
    get() {
      val result = targetObject.getMember("viewState")
      if(result == "undefined" || result == null)
          throw RuntimeException("'viewState' not exists in underlying js object")
      return result as Any
    }

  public override val contributionsState: Any
    get() {
      val result = targetObject.getMember("contributionsState")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contributionsState' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : ICodeEditorViewState> new(
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
