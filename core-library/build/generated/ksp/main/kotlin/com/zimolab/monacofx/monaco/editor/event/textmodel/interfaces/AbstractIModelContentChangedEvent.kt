package com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelContentChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-05T00:40:10.821500700
 */
public abstract class AbstractIModelContentChangedEvent(
  public override val targetObject: JSObject
) : IModelContentChangedEvent, JsInterfaceObject {
  public override val changes: Any
    get() {
      val result = targetObject.getMember("changes")
      if(result == "undefined" || result == null)
          throw RuntimeException("'changes' not exists in underlying js object")
      return result as Any
    }

  public override val eol: String
    get() {
      val result = targetObject.getMember("eol")
      if(result == "undefined" || result == null)
          throw RuntimeException("'eol' not exists in underlying js object")
      return result as String
    }

  public override val versionId: Int
    get() {
      val result = targetObject.getMember("versionId")
      if(result == "undefined" || result == null)
          throw RuntimeException("'versionId' not exists in underlying js object")
      return result as Int
    }

  public override val isUndoing: Boolean
    get() {
      val result = targetObject.getMember("isUndoing")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isUndoing' not exists in underlying js object")
      return result as Boolean
    }

  public override val isRedoing: Boolean
    get() {
      val result = targetObject.getMember("isRedoing")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isRedoing' not exists in underlying js object")
      return result as Boolean
    }

  public override val isFlush: Boolean
    get() {
      val result = targetObject.getMember("isFlush")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isFlush' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IModelContentChangedEvent> new(
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
