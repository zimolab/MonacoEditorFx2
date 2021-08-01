package com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces

import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.textmodel.interfaces.IModelDecorationsChangedEvent".It may
 * be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-01T23:11:49.486414700
 */
public abstract class AbstractIModelDecorationsChangedEvent(
  public val targetObject: JSObject
) : IModelDecorationsChangedEvent {
  public override val affectsMinimap: Boolean
    get() {
      val result = targetObject.getMember("affectsMinimap")
      if(result == "undefined" || result == null)
          throw RuntimeException("'affectsMinimap' not exists in underlying js object")
      return result as Boolean
    }

  public override val affectsOverviewRuler: Boolean
    get() {
      val result = targetObject.getMember("affectsOverviewRuler")
      if(result == "undefined" || result == null)
          throw RuntimeException("'affectsOverviewRuler' not exists in underlying js object")
      return result as Boolean
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
