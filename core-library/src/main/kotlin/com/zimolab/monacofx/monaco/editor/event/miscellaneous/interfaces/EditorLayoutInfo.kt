package com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface IEditorLayoutInfo {
    val width: Int
    val height: Int
    val glyphMarginLeft: Int
    val glyphMarginWidth: Int
    val lineNumbersLeft: Int
    val lineNumbersWidth: Int
    val decorationsLeft: Int
    val decorationsWidth: Int
    val contentLeft: Int
    val contentWidth: Int
    val minimap: Any
    val viewportColumn: Int
    val isWordWrapMinified: Boolean
    val isViewportWrapping: Boolean
    val wrappingColumn: Int
    val verticalScrollbarWidth: Int
    val horizontalScrollbarHeight: Int
    val overviewRuler: Any
}

@JsInterface
interface IOverviewRulerPosition {
    val width: Int
    val height: Int
    val top: Int
    val right: Int
}

@JsInterface
interface IEditorMinimapLayoutInfo {
    val renderMinimap: Int
    val minimapLeft: Int
    val minimapWidth: Int
    val minimapHeightIsEditorHeight: Boolean
    val minimapIsSampling: Boolean
    val minimapScale: Int
    val minimapLineHeight: Int
    val minimapCanvasInnerWidth: Int
    val minimapCanvasInnerHeight: Int
    val minimapCanvasOuterWidth: Int
    val minimapCanvasOuterHeight: Int
}
