package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import javafx.scene.web.WebEngine
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.reflect.KFunction
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.IViewState".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-09T01:15:59.250745600
 */
public abstract class AbstractIViewState(
  public override val targetObject: JSObject
) : IViewState, JsInterfaceObject {
  public override val scrollTop: Int?
    get() {
      var result = targetObject.getMember("scrollTop")
      if(result == "undefined")
          result = null
      return result?.let { it as Int }
    }

  public override val scrollTopWithoutViewZones: Int?
    get() {
      var result = targetObject.getMember("scrollTopWithoutViewZones")
      if(result == "undefined")
          result = null
      return result?.let { it as Int }
    }

  public override val scrollLeft: Int
    get() {
      val result = targetObject.getMember("scrollLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scrollLeft' not exists in underlying js object")
      return result as Int
    }

  public override val firstPosition: Any
    get() {
      val result = targetObject.getMember("firstPosition")
      if(result == "undefined" || result == null)
          throw RuntimeException("'firstPosition' not exists in underlying js object")
      return result as Any
    }

  public override val firstPositionDeltaTop: Int
    get() {
      val result = targetObject.getMember("firstPositionDeltaTop")
      if(result == "undefined" || result == null)
          throw RuntimeException("'firstPositionDeltaTop' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IViewState> new(
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
