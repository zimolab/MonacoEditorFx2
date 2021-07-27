package com.zimolab.monacofx.monaco.editor.enums

enum class TrackedRangeStickiness(val value: Int = Counter.nextValue) {
    AlwaysGrowsWhenTypingAtEdges(0) /* = 0 */,
    NeverGrowsWhenTypingAtEdges /* = 1 */,
    GrowsOnlyWhenTypingBefore /* = 2 */,
    GrowsOnlyWhenTypingAfter /* = 3 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}