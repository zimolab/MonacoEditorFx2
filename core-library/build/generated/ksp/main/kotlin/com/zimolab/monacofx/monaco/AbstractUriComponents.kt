package com.zimolab.monacofx.monaco

import kotlin.Boolean
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from "com.zimolab.monacofx.monaco.UriComponents".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-02T01:28:50.081744900
 */
public abstract class AbstractUriComponents(
  public val targetObject: JSObject
) : UriComponents {
  public override val authority: String?
    get() {
      val result = targetObject.getMember("authority")
      if(result == "undefined" || result == null)
          throw RuntimeException("'authority' not exists in underlying js object")
      return result as String
    }

  public override val fragment: String?
    get() {
      val result = targetObject.getMember("fragment")
      if(result == "undefined" || result == null)
          throw RuntimeException("'fragment' not exists in underlying js object")
      return result as String
    }

  public override val path: String?
    get() {
      val result = targetObject.getMember("path")
      if(result == "undefined" || result == null)
          throw RuntimeException("'path' not exists in underlying js object")
      return result as String
    }

  public override val query: String?
    get() {
      val result = targetObject.getMember("query")
      if(result == "undefined" || result == null)
          throw RuntimeException("'query' not exists in underlying js object")
      return result as String
    }

  public override val scheme: String?
    get() {
      val result = targetObject.getMember("scheme")
      if(result == "undefined" || result == null)
          throw RuntimeException("'scheme' not exists in underlying js object")
      return result as String
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
