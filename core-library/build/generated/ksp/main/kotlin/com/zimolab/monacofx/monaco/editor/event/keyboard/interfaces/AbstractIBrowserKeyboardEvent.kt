package com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces

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
 * "com.zimolab.monacofx.monaco.editor.event.keyboard.interfaces.IBrowserKeyboardEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T01:53:24.239812100
 */
public abstract class AbstractIBrowserKeyboardEvent(
  public override val targetObject: JSObject
) : IBrowserKeyboardEvent, JsInterfaceObject {
  public override val altKey: Boolean
    get() {
      val result = targetObject.getMember("altKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'altKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val bubbles: Boolean
    get() {
      val result = targetObject.getMember("bubbles")
      if(result == "undefined" || result == null)
          throw RuntimeException("'bubbles' not exists in underlying js object")
      return result as Boolean
    }

  public override val cancelBubble: Boolean
    get() {
      val result = targetObject.getMember("cancelBubble")
      if(result == "undefined" || result == null)
          throw RuntimeException("'cancelBubble' not exists in underlying js object")
      return result as Boolean
    }

  public override val cancelable: Boolean
    get() {
      val result = targetObject.getMember("cancelable")
      if(result == "undefined" || result == null)
          throw RuntimeException("'cancelable' not exists in underlying js object")
      return result as Boolean
    }

  public override val charCode: Int
    get() {
      val result = targetObject.getMember("charCode")
      if(result == "undefined" || result == null)
          throw RuntimeException("'charCode' not exists in underlying js object")
      return result as Int
    }

  public override val code: String
    get() {
      val result = targetObject.getMember("code")
      if(result == "undefined" || result == null)
          throw RuntimeException("'code' not exists in underlying js object")
      return result as String
    }

  public override val composed: Boolean
    get() {
      val result = targetObject.getMember("composed")
      if(result == "undefined" || result == null)
          throw RuntimeException("'composed' not exists in underlying js object")
      return result as Boolean
    }

  public override val ctrlKey: Boolean
    get() {
      val result = targetObject.getMember("ctrlKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'ctrlKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val defaultPrevented: Boolean
    get() {
      val result = targetObject.getMember("defaultPrevented")
      if(result == "undefined" || result == null)
          throw RuntimeException("'defaultPrevented' not exists in underlying js object")
      return result as Boolean
    }

  public override val detail: Int
    get() {
      val result = targetObject.getMember("detail")
      if(result == "undefined" || result == null)
          throw RuntimeException("'detail' not exists in underlying js object")
      return result as Int
    }

  public override val eventPhase: Int
    get() {
      val result = targetObject.getMember("eventPhase")
      if(result == "undefined" || result == null)
          throw RuntimeException("'eventPhase' not exists in underlying js object")
      return result as Int
    }

  public override val isComposing: Boolean
    get() {
      val result = targetObject.getMember("isComposing")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isComposing' not exists in underlying js object")
      return result as Boolean
    }

  public override val isTrusted: Boolean
    get() {
      val result = targetObject.getMember("isTrusted")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isTrusted' not exists in underlying js object")
      return result as Boolean
    }

  public override val key: String
    get() {
      val result = targetObject.getMember("key")
      if(result == "undefined" || result == null)
          throw RuntimeException("'key' not exists in underlying js object")
      return result as String
    }

  public override val keyCode: Int
    get() {
      val result = targetObject.getMember("keyCode")
      if(result == "undefined" || result == null)
          throw RuntimeException("'keyCode' not exists in underlying js object")
      return result as Int
    }

  public override val location: Int
    get() {
      val result = targetObject.getMember("location")
      if(result == "undefined" || result == null)
          throw RuntimeException("'location' not exists in underlying js object")
      return result as Int
    }

  public override val metaKey: Boolean
    get() {
      val result = targetObject.getMember("metaKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'metaKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val repeat: Boolean
    get() {
      val result = targetObject.getMember("repeat")
      if(result == "undefined" || result == null)
          throw RuntimeException("'repeat' not exists in underlying js object")
      return result as Boolean
    }

  public override val returnValue: Boolean
    get() {
      val result = targetObject.getMember("returnValue")
      if(result == "undefined" || result == null)
          throw RuntimeException("'returnValue' not exists in underlying js object")
      return result as Boolean
    }

  public override val shiftKey: Boolean
    get() {
      val result = targetObject.getMember("shiftKey")
      if(result == "undefined" || result == null)
          throw RuntimeException("'shiftKey' not exists in underlying js object")
      return result as Boolean
    }

  public override val timeStamp: Int
    get() {
      val result = targetObject.getMember("timeStamp")
      if(result == "undefined" || result == null)
          throw RuntimeException("'timeStamp' not exists in underlying js object")
      return result as Int
    }

  public override val type: String
    get() {
      val result = targetObject.getMember("type")
      if(result == "undefined" || result == null)
          throw RuntimeException("'type' not exists in underlying js object")
      return result as String
    }

  public override val which: Int
    get() {
      val result = targetObject.getMember("which")
      if(result == "undefined" || result == null)
          throw RuntimeException("'which' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object {
    public inline fun <reified T : IBrowserKeyboardEvent> new(
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
