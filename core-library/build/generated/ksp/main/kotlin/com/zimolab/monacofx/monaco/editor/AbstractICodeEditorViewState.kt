package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.ICodeEditorViewState".It
 * may be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from
 * it with your own implementation.
 * @2021-08-10T02:37:57.200562700
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

  public companion object
}
