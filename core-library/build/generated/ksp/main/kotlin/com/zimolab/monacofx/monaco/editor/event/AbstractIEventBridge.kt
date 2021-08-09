package com.zimolab.monacofx.monaco.editor.event

import com.zimolab.jsobject.annotations.JsInterfaceObject
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.editor.event.IEventBridge".It may
 * be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-10T02:37:57.244581100
 */
public abstract class AbstractIEventBridge(
  public override val targetObject: JSObject
) : IEventBridge, JsInterfaceObject {
  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public override fun listen(eventId: Int): Boolean {
    val result = targetObject.call("listen", eventId)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun unlisten(eventId: Int): Boolean {
    val result = targetObject.call("unlisten", eventId)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public override fun isListened(eventId: Int): Boolean {
    val result = targetObject.call("isListened", eventId)
    if(result == "undefined" || result !is Boolean)
        throw RuntimeException("return value type is not as expected")
    return result as Boolean
  }

  public companion object
}
