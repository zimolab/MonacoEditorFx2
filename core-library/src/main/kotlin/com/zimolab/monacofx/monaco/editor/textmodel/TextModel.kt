package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.jsbase.*
import com.zimolab.monacofx.monaco.*
import com.zimolab.monacofx.monaco.editor.MonacoEditor
import com.zimolab.monacofx.monaco.editor.enums.EndOfLineSequence
import com.zimolab.monacofx.monaco.editor.event.textmodel.*
import com.zimolab.monacofx.monaco.editor.options.IIdentifiedSingleEditOperation
import com.zimolab.monacofx.monaco.editor.options.TextModelResolvedOptions
import com.zimolab.monacofx.monaco.editor.textmodel.interfaces.*
import netscape.javascript.JSObject

class TextModel(val model: JSObject,
                val monacoEditor: MonacoEditor) : JsBridge, ITextModelEventProcessor {

    init {
        getReady()
    }

    companion object {
        const val NAME_IN_JS = "javaEditorFxModel"
        const val DESCRIPTION = ""
    }

    object JSCODE {
        const val GET_READY = "onReady"
    }

    override fun getJavascriptName(): String = NAME_IN_JS
    override fun getDescription(): String = DESCRIPTION

    fun getReady() {
        invoke(JSCODE.GET_READY, this)

    }

    ////////////////////////////实用方法、辅助方法///////////////////////
    fun execute(jsCode: String): Any? {
        val result = monacoEditor.webEngine.execute(jsCode)
        if (result is Throwable) {
            monacoEditor.monacoFx.internalErrorOccurs(Globals.JS_EXCEPTION, result)
            return null
        }
        return result
    }

    fun invoke(methodName: String, vararg args: Any?): Any? {
        val result = model.invoke(methodName, *args)
        if (result is Throwable) {
            monacoEditor.monacoFx.internalErrorOccurs(Globals.JS_EXCEPTION, result)
            return null
        }
        return result
    }


    //////////////////////////////////////////////////////////////////

    //////////////////////////桥接JS层面ITextModel的事件////////////////
    private val eventMap = mutableMapOf<Int, TextModelEventListener>()

    override fun listen(eventId: Int, callback: TextModelEventListener) {
        if (eventId in eventMap.keys) {
            eventMap.remove(eventId)
        }
        eventMap[eventId] = callback
    }

    override fun unlisten(eventId: Int) {
        if (eventId in eventMap.keys) {
            eventMap.remove(eventId)
        }
    }

    override fun isListened(eventId: Int): Boolean {
        return eventId in eventMap.keys
    }

    // 该方法在JS层面被调用
    fun onTextModelEvent(eventId: Int, event: Any?) {
        if (isListened(eventId)) {
            eventMap[eventId]?.invoke(eventId, event)
        }
    }

    // 将ITextModel中的具体事件映射到kotlin层，以符合语言习惯
    fun onDidChangeContent(listener: ModelContentChangedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { id, event ->
                if (event is JSObject)
                    listener(id, ModelContentChangedEvent(event))
            }
    }

    fun onDidChangeDecorations(listener: ModelDecorationsChangedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { id, event ->
                if (event is JSObject)
                    listener(id, ModelDecorationsChangedEvent(event))
            }
    }

    fun onDidChangeOptions(listener: ModelOptionsChangedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { id, event ->
                if (event is JSObject)
                    listener(id, ModelOptionsChangedEvent(event))
            }
    }

    fun onDidChangeLanguage(listener: ModelLanguageChangedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { id, event ->
                if (event is JSObject)
                    listener(id, ModelLanguageChangedEvent(event))
            }
    }

    fun onDidChangeLanguageConfiguration(listener: ModelChangeLanguageConfigurationListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { id, event ->
                if (event is JSObject)
                    listener(id, event)
            }

    }

    fun onDidChangeAttached(listener: ModelChangeAttachedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { id, _ ->
                listener(id)
            }
    }

    fun onWillDispose(listener: ModelWillDisposeListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeContent)
        else
            listen(TextModelEvents.onDidChangeContent) { eventId, _ ->
                listener(eventId)
            }
    }

    ////////////////////////////////////////////////////////////////


    /////////////////////////////ITextModel APIs////////////////////

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

    fun getValueInRange(range: IRange, eol: Int): String {
        TODO()
    }

    fun getValueLengthInRange(range: IRange): Int {
        TODO()
    }

    fun getCharacterCountInRange(range: IRange): Int {
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
    /////////////////////////////////////////////////////////////
}