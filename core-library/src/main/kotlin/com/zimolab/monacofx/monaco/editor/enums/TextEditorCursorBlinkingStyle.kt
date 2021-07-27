package com.zimolab.monacofx.monaco.editor.enums

enum class TextEditorCursorBlinkingStyle(val value: Int = Counter.nextValue) {
    Hidden(0) /* = 0 */,
    Blink /* = 1 */,
    Smooth /* = 2 */,
    Phase /* = 3 */,
    Expand /* = 4 */,
    Solid /* = 5 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}