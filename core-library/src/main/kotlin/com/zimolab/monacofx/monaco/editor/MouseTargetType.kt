package com.zimolab.monacofx.monaco.editor

enum class MouseTargetType constructor(val value: Int = Counter.nextValue) {
    UNKNOWN(0) /* = 0 */,
    TEXTAREA /* = 1 */,
    GUTTER_GLYPH_MARGIN /* = 2 */,
    GUTTER_LINE_NUMBERS /* = 3 */,
    GUTTER_LINE_DECORATIONS /* = 4 */,
    GUTTER_VIEW_ZONE /* = 5 */,
    CONTENT_TEXT /* = 6 */,
    CONTENT_EMPTY /* = 7 */,
    CONTENT_VIEW_ZONE /* = 8 */,
    CONTENT_WIDGET /* = 9 */,
    OVERVIEW_RULER /* = 10 */,
    SCROLLBAR /* = 11 */,
    OVERLAY_WIDGET /* = 12 */,
    OUTSIDE_EDITOR(13) /* = 13 */;

    private object Counter {
        var nextValue = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}
