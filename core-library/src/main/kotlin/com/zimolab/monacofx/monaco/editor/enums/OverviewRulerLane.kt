package com.zimolab.monacofx.monaco.editor.enums

enum class OverviewRulerLane(val value: Int = Counter.nextValue) {
    Left(1) /* = 1 */,
    Center /* = 2 */,
    Right /* = 4 */,
    Full /* = 7 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}