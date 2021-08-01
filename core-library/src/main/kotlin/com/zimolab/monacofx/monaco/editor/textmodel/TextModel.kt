package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.jsbase.JsBridge
import com.zimolab.monacofx.jsbase.inject
import com.zimolab.monacofx.monaco.*
import com.zimolab.monacofx.monaco.editor.FindMatch
import com.zimolab.monacofx.monaco.editor.IIdentifiedSingleEditOperation
import com.zimolab.monacofx.monaco.editor.TextModelResolvedOptions
import com.zimolab.monacofx.monaco.editor.enums.EndOfLineSequence
import netscape.javascript.JSObject

class TextModel(private val model: JSObject) : JsBridge {
    init {
        model.inject(this)
    }

    companion object {
        const val NAME_IN_JS = "javaEditorFxModel"
    }

    override fun getJavascriptName(): String = NAME_IN_JS
    override fun getDescription(): String = ""

    var id: String = TODO()

    fun getOptions(): TextModelResolvedOptions {
        TODO()
    }

    fun getVersionId(): Int {
        TODO()
    }

    fun getAlternativeVersionId(): Int {
        TODO()
    }

    fun setValue(newValue: String) {
        TODO()
    }

    fun getValue(eol: Int, preserveBOM: Boolean): String {
        TODO()
    }

    fun getValueLength(eol: Int, preserveBOM: Boolean): Int {
        TODO()
    }

    fun getValueInRange(range: RangeObject, eol: Int): String {
        TODO()
    }

    fun getValueLengthInRange(range: RangeObject): Int {
        TODO()
    }

    fun getCharacterCountInRange(range: RangeObject): Int {
        TODO()
    }

    fun getLineCount(): Int {
        TODO()
    }

    fun getLineContent(lineNumber: Int): String {
        TODO()
    }

    fun getLineLength(lineNumber: Int): Int {
        TODO()
    }

    fun getLinesContent(): JsArray {
        TODO()
    }

    fun getEOL(): String {
        TODO()
    }

    fun getEndOfLineSequence(): Int {
        TODO()
    }

    fun getLineMinColumn(lineNumber: Int): Int {
        TODO()
    }

    fun getLineMaxColumn(lineNumber: Int): Int {
        TODO()
    }

    fun getLineFirstNonWhitespaceColumn(lineNumber: Int): Int {
        TODO()
    }

    fun getLineLastNonWhitespaceColumn(lineNumber: Int): Int {
        TODO()
    }

    fun validatePosition(position: IPosition): Position {
        TODO()
    }

    fun modifyPosition(position: IPosition, offset: Int): Position {
        TODO()
    }

    fun validateRange(range: IRange): Range {
        TODO()
    }

    fun getOffsetAt(position: IPosition): Int {
        TODO()
    }

    fun getPositionAt(offset: Int): Position {
        TODO()
    }

    fun getFullModelRange(): Range {
        TODO()
    }

    fun isDisposed(): Boolean {
        TODO()
    }

    fun findMatches(
        searchString: String,
        searchOnlyEditableRange: Boolean,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): Array<FindMatch> {
        TODO()
    }

    fun findMatches(
        searchString: String,
        searchOnlyEditableRange: Boolean,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): Array<FindMatch> {
        TODO()
    }

    fun findMatches(
        searchString: String,
        searchScope: IRange,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): Array<FindMatch> {
        TODO()
    }

    fun findMatches(
        searchString: String,
        searchScope: IRange,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): Array<FindMatch> {
        TODO()
    }

    fun findMatches(
        searchString: String,
        searchScope: Array<IRange>,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): Array<FindMatch> {
        TODO()
    }

    fun findMatches(
        searchString: String,
        searchScope: Array<IRange>,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): Array<FindMatch> {
        TODO()
    }

    fun findNextMatch(
        searchString: String,
        searchStart: IPosition,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): FindMatch? {
        TODO()
    }

    fun findPreviousMatch(
        searchString: String,
        searchStart: IPosition,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): FindMatch? {
        TODO()
    }

    fun getModeId(): String {
        TODO()
    }

    fun getWordAtPosition(position: IPosition): IWordAtPosition? {
        TODO()
    }

    fun getWordUntilPosition(position: IPosition): IWordAtPosition {
        TODO()
    }

    fun deltaDecorations(
        oldDecorations: Array<String>,
        newDecorations: Array<IModelDeltaDecoration>,
        ownerId: Number
    ): Array<String> {
        TODO()
    }

    fun getDecorationOptions(id: String): IModelDecorationOptions? {
        TODO()
    }

    fun getDecorationRange(id: String): Range? {
        TODO()
    }

    fun getLineDecorations(lineNumber: Int, ownerId: Int, filterOutValidation: Boolean): Array<IModelDecoration> {
        TODO()
    }

    fun getLinesDecorations(
        startLineNumber: Int,
        endLineNumber: Int,
        ownerId: Int,
        filterOutValidation: Boolean
    ): Array<IModelDecoration> {
        TODO()
    }

    fun getDecorationsInRange(range: IRange, ownerId: Int, filterOutValidation: Boolean): Array<IModelDecoration> {
        TODO()
    }

    fun getAllDecorations(ownerId: Int, filterOutValidation: Boolean): Array<IModelDecoration> {
        TODO()
    }

    fun getOverviewRulerDecorations(ownerId: Int, filterOutValidation: Boolean): Array<IModelDecoration> {
        TODO()
    }

    fun getInjectedTextDecorations(ownerId: Int): Array<IModelDecoration> {
        TODO()
    }

    fun normalizeIndentation(str: String): String {
        TODO()
    }

    fun updateOptions(newOpts: ITextModelUpdateOptions) {
        TODO()
    }

    fun detectIndentation(defaultInsertSpaces: Boolean, defaultTabSize: Int) {
        TODO()
    }

    fun pushStackElement() {
        TODO()
    }

    fun popStackElement() {
        TODO()
    }

    fun pushEditOperations(
        beforeCursorState: Array<Selection>?,
        editOperations: Array<IIdentifiedSingleEditOperation>,
        cursorStateComputer: ICursorStateComputer
    ): Array<Selection>? {
        TODO()
    }

    fun pushEOL(eol: EndOfLineSequence) {
        TODO()
    }

    fun applyEdits(operations: Array<IIdentifiedSingleEditOperation>) {
        TODO()
    }

    fun applyEdits(
        operations: Array<IIdentifiedSingleEditOperation>,
        computeUndoEdits: Boolean
    ): Array<Any>? /* Unit | Array */ {
        TODO()
    }

    fun setEOL(eol: EndOfLineSequence) {
        TODO()
    }

    fun dispose() {
        TODO()
    }

    fun isAttachedToEditor(): Boolean {
        TODO()
    }
}