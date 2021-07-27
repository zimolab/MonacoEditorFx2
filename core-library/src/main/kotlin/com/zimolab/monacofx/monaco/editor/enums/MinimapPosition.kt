package com.zimolab.monacofx.monaco.editor.enums

enum class MinimapPosition(val value: Int = Counter.nextValue) {
    Inline(1) /* = 1 */,
    Gutter /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}