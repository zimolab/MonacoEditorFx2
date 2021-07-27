package com.zimolab.monacofx.monaco.editor.enums

enum class TextEditorCursorStyle(val value: Int = Counter.nextValue) {
    Line(1) /* = 1 */,
    Block /* = 2 */,
    Underline /* = 3 */,
    LineThin /* = 4 */,
    BlockOutline /* = 5 */,
    UnderlineThin /* = 6 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}