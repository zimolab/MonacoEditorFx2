package com.zimolab.monacofx.monaco.editor.enums

enum class RenderMinimap(val value: Int = Counter.nextValue) {
    None(0) /* = 0 */,
    Text /* = 1 */,
    Blocks /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}