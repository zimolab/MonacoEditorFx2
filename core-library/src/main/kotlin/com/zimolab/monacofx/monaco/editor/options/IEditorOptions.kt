package com.zimolab.monacofx.monaco.editor.options

open class IEditorOptions (
    open val inDiffEditor: Boolean? = null,
    open val ariaLabel: String? = null,
    open val tabIndex: Int? = null,
    open val rulers: Array<IRulerOption>? = null,/* _root_ide_package_.kotlin.Int | IRulerOption */
    open val wordSeparators: String? = null,
    open val selectionClipboard: Boolean? = null,
    open val lineInts: String? = null, /* "on" | "off" | "relative" | "interval" | ((line_root_ide_package_.kotlin.Int: _root_ide_package_.kotlin.Int) -> String)? = null, */
    open val cursorSurroundingLines: Int? = null,
    open val cursorSurroundingLinesStyle: String? = null, /* "default" | "all" */
    open val renderFinalNewline: Boolean? = null,
    open val unusualLineTerminators: String? = null, /* "auto" | "off" | "prompt" */
    open val selectOnLineInts: Boolean? = null,
    open val lineIntsMinChars: Int? = null,
    open val glyphMargin: Boolean? = null,
    open val lineDecorationsWidth: String? = null, /* _root_ide_package_.kotlin.Int? = null, | String? = null, */
    open val revealHorizontalRightPadding: Int? = null,
    open val roundedSelection: Boolean? = null,
    open val extraEditorClassName: String? = null,
    open val readOnly: Boolean? = null,
    open val domReadOnly: Boolean? = null,
    open val linkedEditing: Boolean? = null,
    open val renameOnType: Boolean? = null,
    open val renderValidationDecorations: String? = null, /* "editable" | "on" | "off" */
    open val scrollbar: IEditorScrollbarOptions? = null,
    open val minimap: IEditorMinimapOptions? = null,
    open val find: IEditorFindOptions? = null,
    open val fixedOverflowWidgets: Boolean? = null,
    open val overviewRulerLanes: Int? = null,
    open val overviewRulerBorder: Boolean? = null,
    open val cursorBlinking: String? = null, /* "blink" | "smooth" | "phase" | "expand" | "solid" */
    open val mouseWheelZoom: Boolean? = null,
    open val mouseStyle: String? = null, /* "text" | "default" | "copy" */
    open val cursorSmoothCaretAnimation: Boolean? = null,
    open val cursorStyle: String? = null, /* "line" | "block" | "underline" | "line-thin" | "block-outline" | "underline-thin" */
    open val cursorWidth: Int? = null,
    open val fontLigatures: Boolean? = null, /* Boolean? = null, | String? = null, */
    open val disableLayerHinting: Boolean? = null,
    open val disableMonospaceOptimizations: Boolean? = null,
    open val hideCursorInOverviewRuler: Boolean? = null,
    open val scrollBeyondLastLine: Boolean? = null,
    open val scrollBeyondLastColumn: Int? = null,
    open val smoothScrolling: Boolean? = null,
    open val automaticLayout: Boolean? = null,
    open val wordWrap: String? = null, /* "off" | "on" | "wordWrapColumn" | "bounded" */
    open val wordWrapOverride1: String? = null, /* "off" | "on" | "inherit" */
    open val wordWrapOverride2: String? = null, /* "off" | "on" | "inherit" */
    open val wordWrapColumn: Int? = null,
    open val wrappingIndent: String? = null, /* "none" | "same" | "indent" | "deepIndent" */
    open val wrappingStrategy: String? = null, /* "simple" | "advanced" */
    open val wordWrapBreakBeforeCharacters: String? = null,
    open val wordWrapBreakAfterCharacters: String? = null,
    open val stopRenderingLineAfter: Int? = null,
    open val hover: IEditorHoverOptions? = null,
    open val links: Boolean? = null,
    open val colorDecorators: Boolean? = null,
    open val comments: IEditorCommentsOptions? = null,
    open val contextmenu: Boolean? = null,
    open val mouseWheelScrollSensitivity: Int? = null,
    open val fastScrollSensitivity: Int? = null,
    open val scrollPredominantAxis: Boolean? = null,
    open val columnSelection: Boolean? = null,
    open val multiCursorModifier: String? = null, /* "ctrlCmd" | "alt" */
    open val multiCursorMergeOverlapping: Boolean? = null,
    open val multiCursorPaste: String? = null, /* "spread" | "full" */
    open val accessibilitySupport: String? = null, /* "auto" | "off" | "on" */
    open val accessibilityPageSize: Int? = null,
    open val suggest: ISuggestOptions? = null,
    open val inlineSuggest: IInlineSuggestOptions? = null,
    open val smartSelect: ISmartSelectOptions? = null,
    open val gotoLocation: IGotoLocationOptions? = null,
    open val quickSuggestions: IQuickSuggestionsOptions? = null, /* Boolean? = null, | IQuickSuggestionsOptions? = null, */
    open val quickSuggestionsDelay: Int? = null,
    open val padding: IEditorPaddingOptions? = null,
    open val parameterHints: IEditorParameterHintOptions? = null,
    open val autoClosingBrackets: String? = null, /* "always" | "languageDefined" | "beforeWhitespace" | "never" */
    open val autoClosingQuotes: String? = null, /* "always" | "languageDefined" | "beforeWhitespace" | "never" */
    open val autoClosingDelete: String? = null, /* "always" | "auto" | "never" */
    open val autoClosingOvertype: String? = null, /* "always" | "auto" | "never" */
    open val autoSurround: String? = null, /* "languageDefined" | "quotes" | "brackets" | "never" */
    open val autoIndent: String? = null, /* "none" | "keep" | "brackets" | "advanced" | "full" */
    open val stickyTabStops: Boolean? = null,
    open val formatOnType: Boolean? = null,
    open val formatOnPaste: Boolean? = null,
    open val dragAndDrop: Boolean? = null,
    open val suggestOnTriggerCharacters: Boolean? = null,
    open val acceptSuggestionOnEnter: String? = null, /* "on" | "smart" | "off" */
    open val acceptSuggestionOnCommitCharacter: Boolean? = null,
    open val snippetSuggestions: String? = null, /* "top" | "bottom" | "inline" | "none" */
    open val emptySelectionClipboard: Boolean? = null,
    open val copyWithSyntaxHighlighting: Boolean? = null,
    open val suggestSelection: String? = null, /* "first" | "recentlyUsed" | "recentlyUsedByPrefix" */
    open val suggestFontSize: Int? = null,
    open val suggestLineHeight: Int? = null,
    open val tabCompletion: String? = null, /* "on" | "off" | "onlySnippets" */
    open val selectionHighlight: Boolean? = null,
    open val occurrencesHighlight: Boolean? = null,
    open val codeLens: Boolean? = null,
    open val codeLensFontFamily: String? = null,
    open val codeLensFontSize: Int? = null,
    open val lightbulb: IEditorLightbulbOptions? = null,
    open val codeActionsOnSaveTimeout: Int? = null,
    open val folding: Boolean? = null,
    open val foldingStrategy: String? = null, /* "auto" | "indentation" */
    open val foldingHighlight: Boolean? = null,
    open val showFoldingControls: String? = null, /* "always" | "mouseover" */
    open val unfoldOnClickAfterEndOfLine: Boolean? = null,
    open val matchBrackets: String? = null, /* "never" | "near" | "always" */
    open val renderWhitespace: String? = null, /* "none" | "boundary" | "selection" | "trailing" | "all" */
    open val renderControlCharacters: Boolean? = null,
    open val renderIndentGuides: Boolean? = null,
    open val highlightActiveIndentGuide: Boolean? = null,
    open val renderLineHighlight: String? = null, /* "none" | "gutter" | "line" | "all" */
    open val renderLineHighlightOnlyWhenFocus: Boolean? = null,
    open val useTabStops: Boolean? = null,
    open val fontFamily: String? = null,
    open val fontWeight: String? = null,
    open val fontSize: Int? = null,
    open val lineHeight: Int? = null,
    open val letterSpacing: Int? = null,
    open val showUnused: Boolean? = null,
    open val peekWidgetDefaultFocus: String? = null, /* "tree" | "editor" */
    open val definitionLinkOpensInPeek: Boolean? = null,
    open val showDeprecated: Boolean? = null,
    open val inlayHints: IEditorInlayHintsOptions? = null,
    open val useShadowDOM: Boolean? = null,
)