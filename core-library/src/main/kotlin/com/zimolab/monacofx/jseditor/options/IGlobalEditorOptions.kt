package com.zimolab.monacofx.jseditor.options
import com.alibaba.fastjson.*
import com.alibaba.fastjson.annotation.JSONField

open class IGlobalEditorOptions(
    open val tabSize: Int? = null,
    open val insertSpaces: Boolean? = null,
    open val detectIndentation: Boolean? = null,
    open val trimAutoWhitespace: Boolean? = null,
    open val largeFileOptimizations: Boolean? = null,
    open val wordBasedSuggestions: Boolean? = null,
    open val wordBasedSuggestionsOnlySameLanguage: Boolean? = null,
    @JSONField(name = "semanticHighlighting.enabled")
    open val semanticHighlightingEnabled: Boolean? = null /* Boolean? = null | "configuredByTheme" */,
    open val stablePeek: Boolean? = null,
    open val maxTokenizationLineLength: Int? = null,
    open val theme: String? = null,
    open val autoDetectHighContrast: Boolean? = null,
)