package com.zimolab.monacofx.monaco.editor.event.miscellaneous

import com.zimolab.monacofx.monaco.editor.event.miscellaneous.interfaces.AbstractIEditorLayoutInfo
import netscape.javascript.JSObject

class EditorLayoutInfo(jsObject: JSObject): AbstractIEditorLayoutInfo(jsObject) {
    private val _minimap by lazy {
        EditorMinimapLayoutInfo(super.minimap as JSObject)
    }

    private val _overviewRuler by lazy {
        OverviewRulerPosition(super.overviewRuler as JSObject)
    }

    override val minimap: EditorMinimapLayoutInfo
        get() = _minimap
    override val overviewRuler: OverviewRulerPosition
        get() = _overviewRuler
}