package com.zimolab.monacofx.monaco.editor.enums

enum class DefaultEndOfLine (val value: Int = Counter.nextValue){
    LF(1) /* = 1 */,
    CRLF /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}