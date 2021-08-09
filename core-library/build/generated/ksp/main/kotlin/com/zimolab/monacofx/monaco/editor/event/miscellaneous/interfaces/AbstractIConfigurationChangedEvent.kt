package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IConfigurationChangedEvent".It
 * may be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from
 * it with your own implementation.
 * @2021-08-10T02:37:57.311579700
 */
public abstract class AbstractIConfigurationChangedEvent(
  public override val targetObject: JSObject
) : IConfigurationChangedEvent, JsInterfaceObject {
  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun hasChanged(editorOption: Int): Boolean {
    val result = targetObject.call("hasChanged", editorOption)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public companion object
}
