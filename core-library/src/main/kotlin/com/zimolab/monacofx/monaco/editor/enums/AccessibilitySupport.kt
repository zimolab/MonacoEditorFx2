package com.zimolab.monacofx.monaco.editor.enums

enum class AccessibilitySupport(val value: Int = Counter.nextValue){
    Unknown(0) /* = 0 */,
    Disabled /* = 1 */,
    Enabled(2) /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}