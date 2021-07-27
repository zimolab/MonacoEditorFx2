package com.zimolab.monacofx.monaco.editor.enums

enum class EndOfLineSequence(val value: Int = Counter.nextValue) {
    LF(0) /* = 0 */,
    CRLF /* = 1 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}