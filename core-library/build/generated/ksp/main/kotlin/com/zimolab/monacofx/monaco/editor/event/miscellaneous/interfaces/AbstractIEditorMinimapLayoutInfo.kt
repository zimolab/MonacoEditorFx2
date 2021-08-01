package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IEditorMinimapLayoutInfo".It may
 * be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-02T01:28:50.169765300
 */
public abstract class AbstractIEditorMinimapLayoutInfo(
  public val targetObject: JSObject
) : IEditorMinimapLayoutInfo {
  public override val renderMinimap: Int
    get() {
      val result = targetObject.getMember("renderMinimap")
      if(result == "undefined" || result == null)
          throw RuntimeException("'renderMinimap' not exists in underlying js object")
      return result as Int
    }

  public override val minimapLeft: Int
    get() {
      val result = targetObject.getMember("minimapLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapLeft' not exists in underlying js object")
      return result as Int
    }

  public override val minimapWidth: Int
    get() {
      val result = targetObject.getMember("minimapWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapWidth' not exists in underlying js object")
      return result as Int
    }

  public override val minimapHeightIsEditorHeight: Boolean
    get() {
      val result = targetObject.getMember("minimapHeightIsEditorHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapHeightIsEditorHeight' not exists in underlying js object")
      return result as Boolean
    }

  public override val minimapIsSampling: Boolean
    get() {
      val result = targetObject.getMember("minimapIsSampling")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapIsSampling' not exists in underlying js object")
      return result as Boolean
    }

  public override val minimapScale: Int
    get() {
      val result = targetObject.getMember("minimapScale")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapScale' not exists in underlying js object")
      return result as Int
    }

  public override val minimapLineHeight: Int
    get() {
      val result = targetObject.getMember("minimapLineHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapLineHeight' not exists in underlying js object")
      return result as Int
    }

  public override val minimapCanvasInnerWidth: Int
    get() {
      val result = targetObject.getMember("minimapCanvasInnerWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapCanvasInnerWidth' not exists in underlying js object")
      return result as Int
    }

  public override val minimapCanvasInnerHeight: Int
    get() {
      val result = targetObject.getMember("minimapCanvasInnerHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapCanvasInnerHeight' not exists in underlying js object")
      return result as Int
    }

  public override val minimapCanvasOuterWidth: Int
    get() {
      val result = targetObject.getMember("minimapCanvasOuterWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapCanvasOuterWidth' not exists in underlying js object")
      return result as Int
    }

  public override val minimapCanvasOuterHeight: Int
    get() {
      val result = targetObject.getMember("minimapCanvasOuterHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimapCanvasOuterHeight' not exists in underlying js object")
      return result as Int
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
