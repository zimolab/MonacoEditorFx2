package com.zimolab.monacofx.monaco

enum class MarkerTag(val value: Int = Counter.nextValue) {
    Unnecessary(1),
    Deprecated(2);

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}