package com.zimolab.monacofx.monaco.editor.enums

enum class CursorChangeReason(val value: Int = Counter.nextValue){
    NotSet(0) /* = 0 */,
    ContentFlush /* = 1 */,
    RecoverFromMarkers /* = 2 */,
    Explicit /* = 3 */,
    Paste /* = 4 */,
    Undo /* = 5 */,
    Redo(6) /* = 6 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}