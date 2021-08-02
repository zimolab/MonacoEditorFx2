package com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces

import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelLanguageChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-02T01:28:50.227863300
 */
public abstract class AbstractIModelLanguageChangedEvent(
  public val targetObject: JSObject
) : IModelLanguageChangedEvent {
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

  public companion object
}