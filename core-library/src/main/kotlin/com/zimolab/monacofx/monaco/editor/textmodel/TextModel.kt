package com.zimolab.monacofx.monaco.editor.textmodel

import com.alibaba.fastjson.JSON
import com.zimolab.jsobject.annotations.JsInterfaceObject
import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.jsbase.JsBridge
import com.zimolab.monacofx.jsbase.invoke
import com.zimolab.monacofx.monaco.*
import com.zimolab.monacofx.monaco.editor.JsAPIs
import com.zimolab.monacofx.monaco.editor.MonacoEditor
import com.zimolab.monacofx.monaco.editor.enums.EndOfLinePreference
import com.zimolab.monacofx.monaco.editor.enums.EndOfLineSequence
import com.zimolab.monacofx.monaco.editor.event.EventBridge
import com.zimolab.monacofx.monaco.editor.event.textmodel.*
import com.zimolab.monacofx.monaco.editor.options.IIdentifiedSingleEditOperation
import com.zimolab.monacofx.monaco.editor.textmodel.interfaces.*
import netscape.javascript.JSObject
import kotlin.collections.set

class TextModel(
    val model: JSObject,
    val monacoEditor: MonacoEditor
) : JsBridge {

    init {
        getReady()
    }

    companion object {
        const val NAME_IN_JS = "javaEditorFxModel"
        const val DESCRIPTION = ""
    }

    private val eventBridge by lazy {
        EventBridge(model)
    }

    override fun getJavascriptName(): String = NAME_IN_JS
    override fun getDescription(): String = DESCRIPTION

    fun getReady() {
        invoke(JsAPIs.TextModel.GET_READY, this)

    }

    ////////////////////////////实用方法、辅助方法///////////////////////
    fun execute(jsCode: String): Any? {
        val result = model.eval(jsCode)
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
    private val textModelEvents = mutableMapOf<Int, TextModelEventListener>()

    // 该方法在JS层面被调用
    fun onTextModelEvent(eventId: Int, event: Any?) {
        if (eventId in textModelEvents) {
            textModelEvents[eventId]?.invoke(eventId, event)
        }
    }

    fun listen(eventId: Int, callback: TextModelEventListener): Boolean {
        return if (eventBridge.listen(eventId)) {
            textModelEvents[eventId] = callback
            true
        } else {
            false
        }
    }

    fun unlisten(eventId: Int) {
        if (eventId in textModelEvents) {
            textModelEvents.remove(eventId)
        }
        eventBridge.unlisten(eventId)
    }

    fun isListened(eventId: Int): Boolean {
        return eventBridge.isListened(eventId)
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
            unlisten(TextModelEvents.onDidChangeDecorations)
        else
            listen(TextModelEvents.onDidChangeDecorations) { id, event ->
                if (event is JSObject)
                    listener(id, ModelDecorationsChangedEvent(event))
            }
    }

    fun onDidChangeOptions(listener: ModelOptionsChangedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeOptions)
        else
            listen(TextModelEvents.onDidChangeOptions) { id, event ->
                if (event is JSObject)
                    listener(id, ModelOptionsChangedEvent(event))
            }
    }

    fun onDidChangeLanguage(listener: ModelLanguageChangedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeLanguage)
        else
            listen(TextModelEvents.onDidChangeLanguage) { id, event ->
                if (event is JSObject)
                    listener(id, ModelLanguageChangedEvent(event))
            }
    }

    fun onDidChangeLanguageConfiguration(listener: ModelChangeLanguageConfigurationListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeLanguageConfiguration)
        else
            listen(TextModelEvents.onDidChangeLanguageConfiguration) { id, event ->
                if (event is JSObject)
                    listener(id, event)
            }

    }

    fun onDidChangeAttached(listener: ModelChangeAttachedListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onDidChangeAttached)
        else
            listen(TextModelEvents.onDidChangeAttached) { id, _ ->
                listener(id)
            }
    }

    fun onWillDispose(listener: ModelWillDisposeListener?) {
        if (listener == null)
            unlisten(TextModelEvents.onWillDispose)
        else
            listen(TextModelEvents.onWillDispose) { eventId, _ ->
                listener(eventId)
            }
    }

    ////////////////////////////////////////////////////////////////


    /////////////////////////////ITextModel APIs////////////////////

    /**
     * 获取TextModel的配置项
     * @return TextModelResolvedOptions?
     */
    fun getOptions(): TextModelResolvedOptions? {
        return when (val r = invoke(JsAPIs.TextModel.GET_OPTIONS)) {
            is JSObject -> TextModelResolvedOptions(r)
            else -> null
        }
    }

    /**
     * 获取TextModel当前版本 ID。任何时候模型发生更改（包括撤消、重做），版本 ID 都会增加。
     * @return Int?
     */
    fun getVersionId(): Int? {
        return invoke(JsAPIs.TextModel.GET_VERSION_ID) as? Int
    }

    /**
     * 获取替代版本 ID。这个替代版本 id 并不总是递增，它会在 undo-redo 的情况下返回相同的值。
     * @return Int?
     */
    fun getAlternativeVersionId(): Int? {
        return invoke(JsAPIs.TextModel.GET_ALTERNATIVE_VERSION_ID) as? Int
    }

    /**
     * 设置文本
     * @param newValue String
     * @return Boolean
     */
    fun setValue(newValue: String): Boolean {
        return invoke(JsAPIs.TextModel.SET_VALUE, newValue) == true
    }

    /**
     * 获取当前文本
     * @param eol Int
     * @param preserveBOM Boolean
     * @return String?
     */
    fun getValue(eol: Int = EndOfLinePreference.TextDefined.value, preserveBOM: Boolean): String? {
        return invoke(JsAPIs.TextModel.GET_VALUE, eol, preserveBOM) as? String
    }

    /**
     * 获取存储在此模型中的文本长度。
     * @param eol Int
     * @param preserveBOM Boolean
     * @return Int?
     */
    fun getValueLength(eol: Int = EndOfLinePreference.TextDefined.value, preserveBOM: Boolean): Int? {
        return invoke(JsAPIs.TextModel.GET_VALUE_LENGTH, eol, preserveBOM) as? Int
    }

    fun getValueInRange(range: IRange, eol: Int = EndOfLinePreference.TextDefined.value): String? {
        return if (range is JsInterfaceObject)
            invoke(JsAPIs.TextModel.GET_VALUE_IN_RANGE, range.targetObject, eol) as? String
        else
            execute("this.${JsAPIs.TextModel.GET_VALUE_IN_RANGE}(${JSON.toJSONString(range)}, $eol)") as? String
    }

    fun getValueLengthInRange(range: IRange): Int? {
        return if (range is JsInterfaceObject)
            invoke(JsAPIs.TextModel.GET_VALUE_LENGTH_IN_RANGE, range.targetObject) as? Int
        else
            execute("this.${JsAPIs.TextModel.GET_VALUE_LENGTH_IN_RANGE}(${JSON.toJSONString(range)})") as? Int
    }

    fun getCharacterCountInRange(range: IRange): Int? {
        return if (range is JsInterfaceObject)
            invoke(JsAPIs.TextModel.GET_CHARACTER_COUNT_IN_RANGE, range.targetObject) as? Int
        else
            execute("this.${JsAPIs.TextModel.GET_CHARACTER_COUNT_IN_RANGE}(${JSON.toJSONString(range)})") as? Int
    }

    fun getLineCount(): Int? {
        return invoke(JsAPIs.TextModel.GET_LINE_COUNT) as? Int
    }

    fun getLineContent(lineNumber: Int): String? {
        return invoke(JsAPIs.TextModel.GET_LINE_CONTENT, lineNumber) as? String
    }

    fun getLineLength(lineNumber: Int): Int? {
        return invoke(JsAPIs.TextModel.GET_LINE_LENGTH, lineNumber) as? Int
    }

    fun getLinesContent(): JsArray? {
        return when (val r = invoke(JsAPIs.TextModel.GET_LINES_CONTENT)) {
            is JSObject -> JsArray(r)
            else -> null
        }
    }

    fun getEOL(): String? {
        return invoke(JsAPIs.TextModel.GET_EOL) as? String
    }

    fun getEndOfLineSequence(): Int? {
        return invoke(JsAPIs.TextModel.GET_END_OF_LINE_SEQUENCE) as? Int

    }

    fun getLineMinColumn(lineNumber: Int): Int? {
        return invoke(JsAPIs.TextModel.GET_LINE_MIN_COLUMN, lineNumber) as? Int
    }

    fun getLineMaxColumn(lineNumber: Int): Int? {
        return invoke(JsAPIs.TextModel.GET_LINE_MAX_COLUMN, lineNumber) as? Int
    }

    fun getLineFirstNonWhitespaceColumn(lineNumber: Int): Int? {
        return invoke(JsAPIs.TextModel.GET_LINE_FIRST_NON_WHITESPACE_COLUMN, lineNumber) as? Int
    }

    fun getLineLastNonWhitespaceColumn(lineNumber: Int): Int? {
        return invoke(JsAPIs.TextModel.GET_LINE_LAST_NON_WHITESPACE_COLUMN, lineNumber) as? Int
    }

    fun validatePosition(position: IPosition): Position? {
        val r = if (position is JsInterfaceObject)
            invoke(JsAPIs.TextModel.VALIDATE_POSITION, position.targetObject) as? JSObject
        else
            execute("this.${JsAPIs.TextModel.VALIDATE_POSITION}(${JSON.toJSONString(position)})") as? JSObject

        return r?.let { Position(it) }
    }

    fun modifyPosition(position: IPosition, offset: Int): Position? {
        val r = if (position is JsInterfaceObject)
            invoke(JsAPIs.TextModel.MODIFY_POSITION, position.targetObject, offset) as? JSObject
        else
            execute("this.${JsAPIs.TextModel.MODIFY_POSITION}(${JSON.toJSONString(position)}, $offset)") as? JSObject

        return r?.let { Position(it) }
    }

    fun validateRange(range: IRange): Range? {
        val r = if (range is JsInterfaceObject)
            invoke(JsAPIs.TextModel.VALIDATE_POSITION, range.targetObject) as? JSObject
        else
            execute("this.${JsAPIs.TextModel.VALIDATE_POSITION}(${JSON.toJSONString(range)})") as? JSObject

        return r?.let { Range(it) }
    }

    fun getOffsetAt(position: IPosition): Int? {
        return if (position is JsInterfaceObject)
            invoke(JsAPIs.TextModel.GET_OFFSET_AT, position.targetObject) as? Int
        else
            execute("this.${JsAPIs.TextModel.GET_OFFSET_AT}(${JSON.toJSONString(position)})") as? Int
    }

    fun getPositionAt(offset: Int): Position? {
        return invoke(JsAPIs.TextModel.GET_POSITION_AT, offset)?.let {
            if (it is JSObject)
                Position(it)
            else
                null
        }
    }

    fun getFullModelRange(): Range? {
        return invoke(JsAPIs.TextModel.GET_FULL_MODEL_RANGE)?.let {
            if (it is JSObject)
                Range(it)
            else
                null
        }
    }

    fun isDisposed(): Boolean? {
        val result = invoke(JsAPIs.TextModel.IS_DISPOSED)
        return if (result !is Boolean)
            null
        else
            result
    }

    fun findMatches(
        searchString: String,
        searchOnlyEditableRange: Boolean,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): JsArray? {
        return when (val r = invoke(
            JsAPIs.TextModel.FIND_MATCHES,
            searchString,
            searchOnlyEditableRange,
            isRegex,
            matchCase,
            wordSeparators,
            captureMatches,
            limitResultCount
        )) {
            is JSObject -> JsArray(r)
            else -> null
        }
    }

    fun findMatches(
        searchString: String,
        searchOnlyEditableRange: Boolean,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): JsArray? {
        val limit = "undefined"
        val r = if (wordSeparators == null)
            execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", $searchOnlyEditableRange, $isRegex, $matchCase, null, $captureMatches, $limit)")
        else
            execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", $searchOnlyEditableRange, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limit)")

        return if (r is JSObject)
            JsArray(r)
        else
            null
    }

    fun findMatches(
        searchString: String,
        searchScope: IRange,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): JsArray? {
        val r = if (searchScope is JsInterfaceObject) {
            invoke(
                JsAPIs.TextModel.FIND_MATCHES,
                searchString,
                searchScope.targetObject,
                isRegex,
                matchCase,
                wordSeparators,
                captureMatches,
                limitResultCount
            )
        } else {
            if (wordSeparators == null)
                execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
            else
                execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
        }
        return if (r is JSObject)
            JsArray(r)
        else
            null
    }

    fun findMatches(
        searchString: String,
        searchScope: IRange,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): JsArray? {
        val limitResultCount = "undefined"
        val r = if (searchScope is JsInterfaceObject) {
            invoke(
                JsAPIs.TextModel.FIND_MATCHES,
                searchString,
                searchScope.targetObject,
                isRegex,
                matchCase,
                wordSeparators,
                captureMatches
            )
        } else {
            if (wordSeparators == null)
                execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
            else
                execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
        }
        return if (r is JSObject)
            JsArray(r)
        else
            null
    }

    fun findMatches(
        searchString: String,
        searchScope: Array<IRange>,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): JsArray? {
        val r = if (wordSeparators == null)
            execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
        else
            execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
        return if (r is JSObject)
            JsArray(r)
        else
            null
    }

    fun findMatches(
        searchString: String,
        searchScope: JsArray,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
        limitResultCount: Int
    ): JsArray? {
        val r = invoke(
            JsAPIs.TextModel.FIND_MATCHES,
            searchString,
            searchScope.source,
            isRegex,
            matchCase,
            wordSeparators,
            captureMatches,
            limitResultCount
        )
        return if (r is JSObject)
            JsArray(r)
        else
            null
    }

    fun findMatches(
        searchString: String,
        searchScope: Array<IRange>,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): JsArray? {
        val limitResultCount = "undefined"
        val r = if (wordSeparators == null)
            execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
        else
            execute("this.${JsAPIs.TextModel.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
        return if (r is JSObject)
            JsArray(r)
        else
            null
    }

    fun findMatches(
        searchString: String,
        searchScope: JsArray,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean,
    ): JsArray? {
        val r = invoke(
            JsAPIs.TextModel.FIND_MATCHES,
            searchString,
            searchScope.source,
            isRegex,
            matchCase,
            wordSeparators,
            captureMatches
        )
        return if (r is JSObject)
            JsArray(r)
        else
            null
    }


    fun findNextMatch(
        searchString: String,
        searchStart: IPosition,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): IFindMatch? {
        return if (searchStart is JsInterfaceObject) {
            invoke(JsAPIs.TextModel.FIND_NEXT_MATCH, searchStart.targetObject, isRegex, matchCase, wordSeparators, captureMatches)
        } else {
            if (wordSeparators == null)
                execute("this.${JsAPIs.TextModel.FIND_NEXT_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, null, $captureMatches)")
            else
                execute("this.${JsAPIs.TextModel.FIND_NEXT_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches)")

        }?.let {
            if (it is JSObject)
                null
            else
                null
        }
    }

    fun findPreviousMatch(
        searchString: String,
        searchStart: IPosition,
        isRegex: Boolean,
        matchCase: Boolean,
        wordSeparators: String?,
        captureMatches: Boolean
    ): IFindMatch? {
        return if (searchStart is JsInterfaceObject) {
            invoke(JsAPIs.TextModel.FIND_PREVIOUS_MATCH, searchStart.targetObject, isRegex, matchCase, wordSeparators, captureMatches)
        } else {
            if (wordSeparators == null)
                execute("this.${JsAPIs.TextModel.FIND_PREVIOUS_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, null, $captureMatches)")
            else
                execute("this.${JsAPIs.TextModel.FIND_PREVIOUS_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches)")

        }?.let {
            if (it is JSObject)
                null
            else
                null
        }
    }

    fun getWordAtPosition(position: IPosition): WordAtPosition? {
        val r = if (position is JsInterfaceObject) {
            invoke(JsAPIs.TextModel.GET_WORD_AT_POSITION, position.targetObject)
        } else {
            execute("this.${JsAPIs.TextModel.GET_WORD_AT_POSITION}(${JSON.toJSONString(position)})")
        } as? JSObject
        return r?.let { WordAtPosition(it) }
    }

    fun getWordUntilPosition(position: IPosition): WordAtPosition? {
        val r = if (position is JsInterfaceObject) {
            invoke(JsAPIs.TextModel.GET_WORD_UNTIL_POSITION, position.targetObject)
        } else {
            execute("this.${JsAPIs.TextModel.GET_WORD_UNTIL_POSITION}(${JSON.toJSONString(position)})")
        } as? JSObject
        return r?.let { WordAtPosition(it) }
    }

    fun normalizeIndentation(str: String): String? {
        return invoke(JsAPIs.TextModel.NORMALIZE_INDENTATION, str) as? String
    }

    fun updateOptions(newOpts: ITextModelUpdateOptions): Boolean {
        return execute("this.${JsAPIs.TextModel.UPDATE_OPTIONS}(${JSON.toJSONString(newOpts)})") == true
    }

    fun detectIndentation(defaultInsertSpaces: Boolean, defaultTabSize: Int): Boolean {
        return invoke(JsAPIs.TextModel.DETECT_INDENTATION, defaultInsertSpaces, defaultTabSize) == true
    }

    fun pushStackElement(): Boolean {
        return invoke(JsAPIs.TextModel.PUSH_STACK_ELEMENT) == true

    }

    fun popStackElement(): Boolean {
        return invoke(JsAPIs.TextModel.POP_STACK_ELEMENT) == true
    }

    fun pushEditOperations(
        beforeCursorState: Array<ISelection>?,
        editOperations: Array<IIdentifiedSingleEditOperation>,
        cursorStateComputer: ICursorStateComputer
    ): Array<Selection>? {
        TODO()
    }

    fun pushEOL(eol: EndOfLineSequence): Boolean {
        return invoke(JsAPIs.TextModel.PUSH_EOL) == true
    }


    fun applyEdits(
        operations: Array<IIdentifiedSingleEditOperation>,
        computeUndoEdits: Boolean
    ): JsArray? /* null | IValidEditOperation */ {
        return execute("this.${JsAPIs.TextModel.APPLY_EDITS}(${JSON.toJSONString(operations)}, $computeUndoEdits)")?.let {
            if (it is JSObject)
                JsArray(it)
            else
                null
        }
    }

    fun applyEdits(
        operations: JsArray,
        computeUndoEdits: Boolean
    ): JsArray? /* null | IValidEditOperation */ {
        return invoke(JsAPIs.TextModel.APPLY_EDITS, operations.source, computeUndoEdits)?.let {
            if (it is JSObject)
                JsArray(it)
            else
                null
        }
    }

    fun setEOL(eol: Int): Boolean {
        return invoke(JsAPIs.TextModel.SET_EOL, eol) == true

    }

    fun dispose(): Boolean {
        return invoke(JsAPIs.TextModel.DISPOSE) == true
    }

    fun isAttachedToEditor(): Boolean {
        return invoke(JsAPIs.TextModel.IS_ATTACHED_TO_EDITOR) == true

    }
    /////////////////////////////////////////////////////////////
}