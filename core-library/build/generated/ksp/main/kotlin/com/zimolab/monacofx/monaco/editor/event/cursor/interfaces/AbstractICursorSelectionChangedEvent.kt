package com.zimolab.monacofx.monaco.editor.event.cursor.interfaces

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.cursor.interfaces.ICursorSelectionChangedEvent".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T02:37:57.259578900
 */
public abstract class AbstractICursorSelectionChangedEvent(
  public override val targetObject: JSObject
) : ICursorSelectionChangedEvent, JsInterfaceObject {
  public override val selection: Any
    get() {
      val result = targetObject.getMember("selection")
      if(result == "undefined" || result == null)
          throw RuntimeException("'selection' not exists in underlying js object")
      return result as Any
    }

  public override val secondarySelections: Any
    get() {
      val result = targetObject.getMember("secondarySelections")
      if(result == "undefined" || result == null)
          throw RuntimeException("'secondarySelections' not exists in underlying js object")
      return result as Any
    }

  public override val modelVersionId: Int
    get() {
      val result = targetObject.getMember("modelVersionId")
      if(result == "undefined" || result == null)
          throw RuntimeException("'modelVersionId' not exists in underlying js object")
      return result as Int
    }

  public override val oldSelections: Any?
    get() {
      var result = targetObject.getMember("oldSelections")
      if(result == "undefined")
          result = null
      return result?.let { it as Any }
    }

  public override val oldModelVersionId: Int
    get() {
      val result = targetObject.getMember("oldModelVersionId")
      if(result == "undefined" || result == null)
          throw RuntimeException("'oldModelVersionId' not exists in underlying js object")
      return result as Int
    }

  public override val source: String
    get() {
      val result = targetObject.getMember("source")
      if(result == "undefined" || result == null)
          throw RuntimeException("'source' not exists in underlying js object")
      return result as String
    }

  public override val reason: Int
    get() {
      val result = targetObject.getMember("reason")
      if(result == "undefined" || result == null)
          throw RuntimeException("'reason' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
