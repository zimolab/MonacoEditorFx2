package com.zimolab.monacofx.monaco.editor.enums

enum class OverlayWidgetPositionPreference(val value: Int = Counter.nextValue) {
    TOP_RIGHT_CORNER(0) /* = 0 */,
    BOTTOM_RIGHT_CORNER /* = 1 */,
    TOP_CENTER /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}