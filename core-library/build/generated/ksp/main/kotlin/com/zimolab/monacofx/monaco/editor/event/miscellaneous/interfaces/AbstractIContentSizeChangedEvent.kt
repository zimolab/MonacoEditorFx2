package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IContentSizeChangedEvent".It may
 * be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-02T11:32:17.530252400
 */
public abstract class AbstractIContentSizeChangedEvent(
  public override val targetObject: JSObject
) : IContentSizeChangedEvent, JsInterfaceObject {
  public override val contentHeight: Int
    get() {
      val result = targetObject.getMember("contentHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentHeight' not exists in underlying js object")
      return result as Int
    }

  public override val contentHeightChanged: Boolean
    get() {
      val result = targetObject.getMember("contentHeightChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentHeightChanged' not exists in underlying js object")
      return result as Boolean
    }

  public override val contentWidth: Int
    get() {
      val result = targetObject.getMember("contentWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentWidth' not exists in underlying js object")
      return result as Int
    }

  public override val contentWidthChanged: Boolean
    get() {
      val result = targetObject.getMember("contentWidthChanged")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentWidthChanged' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IContentSizeChangedEvent> new(
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
