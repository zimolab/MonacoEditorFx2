package com.zimolab.monacofx.monaco.editor.enums

enum class ScrollType(val value: Int = Counter.nextValue) {
    Smooth(0),
    Immediate(1);

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}