package com.zimolab.monacofx.monaco.editor.enums

enum class RenderLineNumbersType(val value: Int = Counter.nextValue) {
    Off(0 ) /* = 0 */,
    On /* = 1 */,
    Relative /* = 2 */,
    Interval /* = 3 */,
    Custom /* = 4 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}