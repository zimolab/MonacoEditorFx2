package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsInterface

object EditorEvents {
    const val onContextMenu: Int = 0
    const val onDidAttemptReadOnlyEdit: Int = 1
    const val onDidBlurEditorText: Int = 2
    const val onDidBlurEditorWidget: Int = 3
    const val onDidChangeConfiguration: Int = 4
    const val onDidChangeCursorPosition: Int = 5
    const val onDidChangeCursorSelection: Int = 6
    const val onDidChangeModel: Int = 7
    const val onDidChangeModelContent: Int = 8
    const val onDidChangeModelDecorations: Int = 9
    const val onDidChangeModelLanguage: Int = 10
    const val onDidChangeModelLanguageConfiguration: Int = 11
    const val onDidChangeModelOptions: Int = 12
    const val onDidContentSizeChange: Int = 13
    const val onDidDispose: Int = 14
    const val onDidFocusEditorText: Int = 15
    const val onDidFocusEditorWidget: Int = 16
    const val onDidLayoutChange: Int = 17
    const val onDidPaste: Int = 18
    const val onDidScrollChange: Int = 19
    const val onKeyDown: Int = 20
    const val onKeyUp: Int = 21
    const val onMouseDown: Int = 22
    const val onMouseLeave: Int = 23
    const val onMouseMove: Int = 24
    const val onMouseUp: Int = 25
}