package com.zimolab.monacofx.monaco.editor.enums

enum class ContentWidgetPositionPreference(val value: Int = Counter.nextValue){
    EXACT(0) /* = 0 */,
    ABOVE /* = 1 */,
    BELOW(2) /* = 2 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}