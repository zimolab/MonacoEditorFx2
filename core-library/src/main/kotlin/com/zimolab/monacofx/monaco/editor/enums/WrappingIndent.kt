package com.zimolab.monacofx.monaco.editor.enums

enum class WrappingIndent(val value: Int = Counter.nextValue) {
    None(0) /* = 0 */,
    Same /* = 1 */,
    Indent /* = 2 */,
    DeepIndent /* = 3 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}