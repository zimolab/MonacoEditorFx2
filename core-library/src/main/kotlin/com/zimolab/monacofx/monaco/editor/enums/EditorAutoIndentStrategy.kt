package com.zimolab.monacofx.monaco.editor.enums

enum class EditorAutoIndentStrategy (val value: Int = Counter.nextValue){
    None(0) /* = 0 */,
    Keep /* = 1 */,
    Brackets /* = 2 */,
    Advanced /* = 3 */,
    Full(4); /* = 4 */

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}