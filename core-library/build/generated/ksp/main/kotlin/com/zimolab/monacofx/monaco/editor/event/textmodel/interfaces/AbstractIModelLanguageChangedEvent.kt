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
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelLanguageChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T01:53:24.369841500
 */
public abstract class AbstractIModelLanguageChangedEvent(
  public override val targetObject: JSObject
) : IModelLanguageChangedEvent, JsInterfaceObject {
  public override val oldLanguage: String
    get() {
      val result = targetObject.getMember("oldLanguage")
      if(result == "undefined" || result == null)
          throw RuntimeException("'oldLanguage' not exists in underlying js object")
      return result as String
    }

  public override val newLanguage: String
    get() {
      val result = targetObject.getMember("newLanguage")
      if(result == "undefined" || result == null)
          throw RuntimeException("'newLanguage' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IModelLanguageChangedEvent> new(
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
