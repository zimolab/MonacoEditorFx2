package com.zimolab.monacofx.monaco

enum class MarkerSeverity(val value: Int = Counter.nextValue) {
    Hint(1) /* = 1 */,
    Info(2) /* = 2 */,
    Warning(4) /* = 4 */,
    Error(8) /* = 8 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}