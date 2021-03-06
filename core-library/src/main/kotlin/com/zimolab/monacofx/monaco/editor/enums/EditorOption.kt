package com.zimolab.monacofx.monaco.editor.enums

enum class EditorOption(val value: Int = Counter.nextValue) {
    acceptSuggestionOnCommitCharacter(0) /* = 0 */,
    acceptSuggestionOnEnter /* = 1 */,
    accessibilitySupport /* = 2 */,
    accessibilityPageSize /* = 3 */,
    ariaLabel /* = 4 */,
    autoClosingBrackets /* = 5 */,
    autoClosingDelete /* = 6 */,
    autoClosingOvertype /* = 7 */,
    autoClosingQuotes /* = 8 */,
    autoIndent /* = 9 */,
    automaticLayout /* = 10 */,
    autoSurround /* = 11 */,
    codeLens /* = 12 */,
    codeLensFontFamily /* = 13 */,
    codeLensFontSize /* = 14 */,
    colorDecorators /* = 15 */,
    columnSelection /* = 16 */,
    comments /* = 17 */,
    contextmenu /* = 18 */,
    copyWithSyntaxHighlighting /* = 19 */,
    cursorBlinking /* = 20 */,
    cursorSmoothCaretAnimation /* = 21 */,
    cursorStyle /* = 22 */,
    cursorSurroundingLines /* = 23 */,
    cursorSurroundingLinesStyle /* = 24 */,
    cursorWidth /* = 25 */,
    disableLayerHinting /* = 26 */,
    disableMonospaceOptimizations /* = 27 */,
    domReadOnly /* = 28 */,
    dragAndDrop /* = 29 */,
    emptySelectionClipboard /* = 30 */,
    extraEditorClassName /* = 31 */,
    fastScrollSensitivity /* = 32 */,
    find /* = 33 */,
    fixedOverflowWidgets /* = 34 */,
    folding /* = 35 */,
    foldingStrategy /* = 36 */,
    foldingHighlight /* = 37 */,
    unfoldOnClickAfterEndOfLine /* = 38 */,
    fontFamily /* = 39 */,
    fontInfo /* = 40 */,
    fontLigatures /* = 41 */,
    fontSize /* = 42 */,
    fontWeight /* = 43 */,
    formatOnPaste /* = 44 */,
    formatOnType /* = 45 */,
    glyphMargin /* = 46 */,
    gotoLocation /* = 47 */,
    hideCursorInOverviewRuler /* = 48 */,
    highlightActiveIndentGuide /* = 49 */,
    hover /* = 50 */,
    inDiffEditor /* = 51 */,
    inlineSuggest /* = 52 */,
    letterSpacing /* = 53 */,
    lightbulb /* = 54 */,
    lineDecorationsWidth /* = 55 */,
    lineHeight /* = 56 */,
    lineNumbers /* = 57 */,
    lineNumbersMinChars /* = 58 */,
    linkedEditing /* = 59 */,
    links /* = 60 */,
    matchBrackets /* = 61 */,
    minimap /* = 62 */,
    mouseStyle /* = 63 */,
    mouseWheelScrollSensitivity /* = 64 */,
    mouseWheelZoom /* = 65 */,
    multiCursorMergeOverlapping /* = 66 */,
    multiCursorModifier /* = 67 */,
    multiCursorPaste /* = 68 */,
    occurrencesHighlight /* = 69 */,
    overviewRulerBorder /* = 70 */,
    overviewRulerLanes /* = 71 */,
    padding /* = 72 */,
    parameterHints /* = 73 */,
    peekWidgetDefaultFocus /* = 74 */,
    definitionLinkOpensInPeek /* = 75 */,
    quickSuggestions /* = 76 */,
    quickSuggestionsDelay /* = 77 */,
    readOnly /* = 78 */,
    renameOnType /* = 79 */,
    renderControlCharacters /* = 80 */,
    renderIndentGuides /* = 81 */,
    renderFinalNewline /* = 82 */,
    renderLineHighlight /* = 83 */,
    renderLineHighlightOnlyWhenFocus /* = 84 */,
    renderValidationDecorations /* = 85 */,
    renderWhitespace /* = 86 */,
    revealHorizontalRightPadding /* = 87 */,
    roundedSelection /* = 88 */,
    rulers /* = 89 */,
    scrollbar /* = 90 */,
    scrollBeyondLastColumn /* = 91 */,
    scrollBeyondLastLine /* = 92 */,
    scrollPredominantAxis /* = 93 */,
    selectionClipboard /* = 94 */,
    selectionHighlight /* = 95 */,
    selectOnLineNumbers /* = 96 */,
    showFoldingControls /* = 97 */,
    showUnused /* = 98 */,
    snippetSuggestions /* = 99 */,
    smartSelect /* = 100 */,
    smoothScrolling /* = 101 */,
    stickyTabStops /* = 102 */,
    stopRenderingLineAfter /* = 103 */,
    suggest /* = 104 */,
    suggestFontSize /* = 105 */,
    suggestLineHeight /* = 106 */,
    suggestOnTriggerCharacters /* = 107 */,
    suggestSelection /* = 108 */,
    tabCompletion /* = 109 */,
    tabIndex /* = 110 */,
    unusualLineTerminators /* = 111 */,
    useShadowDOM /* = 112 */,
    useTabStops /* = 113 */,
    wordSeparators /* = 114 */,
    wordWrap /* = 115 */,
    wordWrapBreakAfterCharacters /* = 116 */,
    wordWrapBreakBeforeCharacters /* = 117 */,
    wordWrapColumn /* = 118 */,
    wordWrapOverride1 /* = 119 */,
    wordWrapOverride2 /* = 120 */,
    wrappingIndent /* = 121 */,
    wrappingStrategy /* = 122 */,
    showDeprecated /* = 123 */,
    inlayHints /* = 124 */,
    editorClassName /* = 125 */,
    pixelRatio /* = 126 */,
    tabFocusMode /* = 127 */,
    layoutInfo /* = 128 */,
    wrappingInfo /* = 129 */;

    protected object Counter {
        var nextValue: Int = 0
    }

    init {
        Counter.nextValue = value + 1
    }
}