package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import netscape.javascript.JSObject

/**
 * This class is auto-generated from
 * "com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.IEditorLayoutInfo".It may be
 * overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. Just inherit from it
 * with your own implementation.
 * @2021-08-02T01:28:50.158761400
 */
public abstract class AbstractIEditorLayoutInfo(
  public val targetObject: JSObject
) : IEditorLayoutInfo {
  public override val width: Int
    get() {
      val result = targetObject.getMember("width")
      if(result == "undefined" || result == null)
          throw RuntimeException("'width' not exists in underlying js object")
      return result as Int
    }

  public override val height: Int
    get() {
      val result = targetObject.getMember("height")
      if(result == "undefined" || result == null)
          throw RuntimeException("'height' not exists in underlying js object")
      return result as Int
    }

  public override val glyphMarginLeft: Int
    get() {
      val result = targetObject.getMember("glyphMarginLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'glyphMarginLeft' not exists in underlying js object")
      return result as Int
    }

  public override val glyphMarginWidth: Int
    get() {
      val result = targetObject.getMember("glyphMarginWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'glyphMarginWidth' not exists in underlying js object")
      return result as Int
    }

  public override val lineNumbersLeft: Int
    get() {
      val result = targetObject.getMember("lineNumbersLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'lineNumbersLeft' not exists in underlying js object")
      return result as Int
    }

  public override val lineNumbersWidth: Int
    get() {
      val result = targetObject.getMember("lineNumbersWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'lineNumbersWidth' not exists in underlying js object")
      return result as Int
    }

  public override val decorationsLeft: Int
    get() {
      val result = targetObject.getMember("decorationsLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'decorationsLeft' not exists in underlying js object")
      return result as Int
    }

  public override val decorationsWidth: Int
    get() {
      val result = targetObject.getMember("decorationsWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'decorationsWidth' not exists in underlying js object")
      return result as Int
    }

  public override val contentLeft: Int
    get() {
      val result = targetObject.getMember("contentLeft")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentLeft' not exists in underlying js object")
      return result as Int
    }

  public override val contentWidth: Int
    get() {
      val result = targetObject.getMember("contentWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'contentWidth' not exists in underlying js object")
      return result as Int
    }

  public override val minimap: Any
    get() {
      val result = targetObject.getMember("minimap")
      if(result == "undefined" || result == null)
          throw RuntimeException("'minimap' not exists in underlying js object")
      return result as Any
    }

  public override val viewportColumn: Int
    get() {
      val result = targetObject.getMember("viewportColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'viewportColumn' not exists in underlying js object")
      return result as Int
    }

  public override val isWordWrapMinified: Boolean
    get() {
      val result = targetObject.getMember("isWordWrapMinified")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isWordWrapMinified' not exists in underlying js object")
      return result as Boolean
    }

  public override val isViewportWrapping: Boolean
    get() {
      val result = targetObject.getMember("isViewportWrapping")
      if(result == "undefined" || result == null)
          throw RuntimeException("'isViewportWrapping' not exists in underlying js object")
      return result as Boolean
    }

  public override val wrappingColumn: Int
    get() {
      val result = targetObject.getMember("wrappingColumn")
      if(result == "undefined" || result == null)
          throw RuntimeException("'wrappingColumn' not exists in underlying js object")
      return result as Int
    }

  public override val verticalScrollbarWidth: Int
    get() {
      val result = targetObject.getMember("verticalScrollbarWidth")
      if(result == "undefined" || result == null)
          throw RuntimeException("'verticalScrollbarWidth' not exists in underlying js object")
      return result as Int
    }

  public override val horizontalScrollbarHeight: Int
    get() {
      val result = targetObject.getMember("horizontalScrollbarHeight")
      if(result == "undefined" || result == null)
          throw RuntimeException("'horizontalScrollbarHeight' not exists in underlying js object")
      return result as Int
    }

  public override val overviewRuler: Any
    get() {
      val result = targetObject.getMember("overviewRuler")
      if(result == "undefined" || result == null)
          throw RuntimeException("'overviewRuler' not exists in underlying js object")
      return result as Any
    }

  public open fun exists(name: String): Boolean = targetObject.getMember(name) != "undefined"

  public companion object
}
