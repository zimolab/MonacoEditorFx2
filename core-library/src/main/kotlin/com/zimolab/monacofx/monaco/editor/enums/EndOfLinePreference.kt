package com.zimolab.monacofx.monaco.editor.enums

enum class EndOfLinePreference(val value: Int = Counter.nextValue) {
    TextDefined(0) /* = 0 */,
    LF /* = 1 */,
    CRLF /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}