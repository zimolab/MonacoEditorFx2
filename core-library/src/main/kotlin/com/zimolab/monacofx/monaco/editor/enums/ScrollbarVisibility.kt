package com.zimolab.monacofx.monaco.editor.enums

enum class ScrollbarVisibility(val value: Int = Counter.nextValue) {
    Auto(1) /* = 1 */,
    Hidden /* = 2 */,
    Visible /* = 3 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}