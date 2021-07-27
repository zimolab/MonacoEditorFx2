package com.zimolab.monacofx.jseditor.options

import com.alibaba.fastjson.annotation.JSONField
import com.zimolab.monacofx.jseditor.options.IEditorConstructionOptions

open class IStandaloneEditorConstructionOptions(
    open val value: String? = null,
    open val language: String? = null,
    open val accessibilityHelpUrl: String? = null,

    // 以下来自IGlobalEditorOptions
    open val tabSize: Int? = null,
    open val insertSpaces: Boolean? = null,
    open val detectIndentation: Boolean? = null,
    open val trimAutoWhitespace: Boolean? = null,
    open val largeFileOptimizations: Boolean? = null,
    open val wordBasedSuggestions: Boolean? = null,
    open val wordBasedSuggestionsOnlySameLanguage: Boolean? = null,
    @JSONField(name="semanticHighlighting.enabled")
    open val semanticHighlightingEnabled: Boolean? = null /* Boolean? = null | "configuredByTheme" */,
    open val stablePeek: Boolean? = null,
    open val maxTokenizationLineLength: Int? = null,
    open val theme: String? = null,
    open val autoDetectHighContrast: Boolean? = null,

): IEditorConstructionOptions()