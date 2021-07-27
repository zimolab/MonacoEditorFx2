package com.zimolab.monacofx.monaco

enum class SelectionDirection(val value: Int = Counter.nextValue) {
    LTR(0),
    RTL(1);

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}