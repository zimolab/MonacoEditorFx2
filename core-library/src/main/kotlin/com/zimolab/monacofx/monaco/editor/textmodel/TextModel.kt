package com.zimolab.monacofx.monaco.editor.textmodel

import com.alibaba.fastjson.JSON
import com.zimolab.jsobject.annotations.JsInterfaceObject
import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.jsbase.JsBridge
import com.zimolab.monacofx.jsbase.invoke
import com.zimolab.monacofx.monaco.*
import com.zimolab.monacofx.monaco.editor.MonacoEditor
import com.zimolab.monacofx.monaco.editor.enums.EndOfLinePreference
import com.zimolab.monacofx.monaco.editor.enums.EndOfLineSequence
import com.zimolab.monacofx.monaco.editor.event.textmodel.*
import com.zimolab.monacofx.monaco.editor.options.IIdentifiedSingleEditOperation
import com.zimolab.monacofx.monaco.editor.textmodel.interfaces.*
import netscape.javascript.JSObject
import kotlin.collections.set

class TextModel(
    val model: JSObject,
    val monacoEditor: MonacoEditor
) : JsBridge, ITextModelEventProcessor {

    init {
        getReady()
    }

    companion object {
        const val NAME_IN_JS = "javaEditorFxModel"
        const val DESCRIPTION = ""
    }

    object JSCODE {
        const val GET_READY = "onReady"
        const val DISPOSE = "dispose"
        const val IS_DISPOSED = "isDisposed"
        const val GET_OPTIONS = "getOptions"
        const val GET_VERSION_ID = "getVersionId"
        const val GET_ALTERNATIVE_VERSION_ID = "getAlternativeVersionId"
        const val SET_VALUE = "setValue"
        const val GET_VALUE = "getValue"
        const val GET_VALUE_LENGTH = "getValueLength"
        const val GET_VALUE_IN_RANGE = "getValueInRange"
        const val GET_VALUE_LENGTH_IN_RANGE = "getValueLengthInRange"
        const val GET_CHARACTER_COUNT_IN_RANGE = "getCharacterCountInRange"
        const val GET_LINE_COUNT = "getLineCount"
        const val GET_LINE_CONTENT = "getLineContent"
        const val GET_LINE_LENGTH = "getLineLength"
        const val GET_LINES_CONTENT = "getLinesContent"
        const val GET_EOL = "getEOL"
        const val GET_END_OF_LINE_SEQUENCE = "getEndOfLineSequence"
        const val GET_LINE_MIN_COLUMN = "getLineMinColumn"
        const val GET_LINE_MAX_COLUMN = "getLineMaxColumn"
        const val GET_LINE_FIRST_NON_WHITESPACE_COLUMN = "getLineFirstNonWhitespaceColumn"
        const val GET_LINE_LAST_NON_WHITESPACE_COLUMN = "getLineLastNonWhitespaceColumn"
        const val VALIDATE_POSITION = "validatePosition"
        const val MODIFY_POSITION = "modifyPosition"
        const val VALIDATE_RANGE = "validateRange"
        const val GET_OFFSET_AT = "getOffsetAt"
        const val GET_POSITION_AT = "getPositionAt"
        const val GET_FULL_MODEL_RANGE = "getFullModelRange"
        const val FIND_MATCHES = "findMatches"
        const val FIND_NEXT_MATCH = "findNextMatch"
        const val FIND_PREVIOUS_MATCH = "findPreviousMatch"
        const val GET_WORD_AT_POSITION = "getWordAtPosition"
        const val GET_WORD_UNTIL_POSITION = "getWordUntilPosition"
        const val GET_LINE_DECORATIONS = "getLineDecorations"
        const val GET_LINES_DECORATIONS = "getLinesDecorations"
        const val GET_DECORATIONS_IN_RANGE = "getDecorationsInRange"
        const val NORMALIZE_INDENTATION = "normalizeIndentation"
        const val UPDATE_OPTIONS = "updateOptions"
        const val DETECT_INDENTATION = "detectIndentation"
        const val PUSH_STACK_ELEMENT = "pushStackElement"
        const val POP_STACK_ELEMENT = "popStackElement"
        const val PUSH_EDIT_OPERATIONS = "pushEditOperations"
        const val PUSH_EOL = "pushEOL"
        const val APPLY_EDITS = "applyEdits"
        const val SET_EOL = "setEOL"
        const val IS_ATTACHED_TO_EDITOR = "isAttachedToEditor"
    }

    override fun getJavascriptName(): String = NAME_IN_JS
    override fun getDescription(): String = DESCRIPTION

    fun getReady() {
        invoke(JSCODE.GET_READY, this)

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
        return when (val r = invoke(JSCODE.GET_OPTIONS)) {
            is JSObject -> TextModelResolvedOptions(r)
            else -> null
        }
    }

    /**
     * 获取TextModel当前版本 ID。任何时候模型发生更改（包括撤消、重做），版本 ID 都会增加。
     * @return Int?
     */
    fun getVersionId(): Int? {
        return invoke(JSCODE.GET_VERSION_ID) as? Int
    }

    /**
     * 获取替代版本 ID。这个替代版本 id 并不总是递增，它会在 undo-redo 的情况下返回相同的值。
     * @return Int?
     */
    fun getAlternativeVersionId(): Int? {
        return invoke(JSCODE.GET_ALTERNATIVE_VERSION_ID) as? Int
    }

    /**
     * 设置文本
     * @param newValue String
     * @return Boolean
     */
    fun setValue(newValue: String): Boolean {
        return invoke(JSCODE.SET_VALUE, newValue) == true
    }

    /**
     * 获取当前文本
     * @param eol Int
     * @param preserveBOM Boolean
     * @return String?
     */
    fun getValue(eol: Int = EndOfLinePreference.TextDefined.value, preserveBOM: Boolean): String? {
        return invoke(JSCODE.GET_VALUE, eol, preserveBOM) as? String
    }

    /**
     * 获取存储在此模型中的文本长度。
     * @param eol Int
     * @param preserveBOM Boolean
     * @return Int?
     */
    fun getValueLength(eol: Int = EndOfLinePreference.TextDefined.value, preserveBOM: Boolean): Int? {
        return invoke(JSCODE.GET_VALUE_LENGTH, eol, preserveBOM) as? Int
    }

    fun getValueInRange(range: IRange, eol: Int = EndOfLinePreference.TextDefined.value): String? {
        return if (range is JsInterfaceObject)
            invoke(JSCODE.GET_VALUE_IN_RANGE, range.targetObject, eol) as? String
        else
            execute("this.${JSCODE.GET_VALUE_IN_RANGE}(${JSON.toJSONString(range)}, $eol)") as? String
    }

    fun getValueLengthInRange(range: IRange): Int? {
        return if (range is JsInterfaceObject)
            invoke(JSCODE.GET_VALUE_LENGTH_IN_RANGE, range.targetObject) as? Int
        else
            execute("this.${JSCODE.GET_VALUE_LENGTH_IN_RANGE}(${JSON.toJSONString(range)})") as? Int
    }

    fun getCharacterCountInRange(range: IRange): Int? {
        return if (range is JsInterfaceObject)
            invoke(JSCODE.GET_CHARACTER_COUNT_IN_RANGE, range.targetObject) as? Int
        else
            execute("this.${JSCODE.GET_CHARACTER_COUNT_IN_RANGE}(${JSON.toJSONString(range)})") as? Int
    }

    fun getLineCount(): Int? {
        return invoke(JSCODE.GET_LINE_COUNT) as? Int
    }

    fun getLineContent(lineNumber: Int): String? {
        return invoke(JSCODE.GET_LINE_CONTENT, lineNumber) as? String
    }

    fun getLineLength(lineNumber: Int): Int? {
        return invoke(JSCODE.GET_LINE_LENGTH, lineNumber) as? Int
    }

    fun getLinesContent(): JsArray? {
        return when (val r = invoke(JSCODE.GET_LINES_CONTENT)) {
            is JSObject -> JsArray(r)
            else -> null
        }
    }

    fun getEOL(): String? {
        return invoke(JSCODE.GET_EOL) as? String
    }

    fun getEndOfLineSequence(): Int? {
        return invoke(JSCODE.GET_END_OF_LINE_SEQUENCE) as? Int

    }

    fun getLineMinColumn(lineNumber: Int): Int? {
        return invoke(JSCODE.GET_LINE_MIN_COLUMN, lineNumber) as? Int
    }

    fun getLineMaxColumn(lineNumber: Int): Int? {
        return invoke(JSCODE.GET_LINE_MAX_COLUMN, lineNumber) as? Int
    }

    fun getLineFirstNonWhitespaceColumn(lineNumber: Int): Int? {
        return invoke(JSCODE.GET_LINE_FIRST_NON_WHITESPACE_COLUMN, lineNumber) as? Int
    }

    fun getLineLastNonWhitespaceColumn(lineNumber: Int): Int? {
        return invoke(JSCODE.GET_LINE_LAST_NON_WHITESPACE_COLUMN, lineNumber) as? Int
    }

    fun validatePosition(position: IPosition): Position? {
        val r = if (position is JsInterfaceObject)
            invoke(JSCODE.VALIDATE_POSITION, position.targetObject) as? JSObject
        else
            execute("this.${JSCODE.VALIDATE_POSITION}(${JSON.toJSONString(position)})") as? JSObject

        return r?.let { Position(it) }
    }

    fun modifyPosition(position: IPosition, offset: Int): Position? {
        val r = if (position is JsInterfaceObject)
            invoke(JSCODE.MODIFY_POSITION, position.targetObject, offset) as? JSObject
        else
            execute("this.${JSCODE.MODIFY_POSITION}(${JSON.toJSONString(position)}, $offset)") as? JSObject

        return r?.let { Position(it) }
    }

    fun validateRange(range: IRange): Range? {
        val r = if (range is JsInterfaceObject)
            invoke(JSCODE.VALIDATE_POSITION, range.targetObject) as? JSObject
        else
            execute("this.${JSCODE.VALIDATE_POSITION}(${JSON.toJSONString(range)})") as? JSObject

        return r?.let { Range(it) }
    }

    fun getOffsetAt(position: IPosition): Int? {
        return if (position is JsInterfaceObject)
            invoke(JSCODE.GET_OFFSET_AT, position.targetObject) as? Int
        else
            execute("this.${JSCODE.GET_OFFSET_AT}(${JSON.toJSONString(position)})") as? Int
    }

    fun getPositionAt(offset: Int): Position? {
        return invoke(JSCODE.GET_POSITION_AT, offset)?.let {
            if (it is JSObject)
                Position(it)
            else
                null
        }
    }

    fun getFullModelRange(): Range? {
        return invoke(JSCODE.GET_FULL_MODEL_RANGE)?.let {
            if (it is JSObject)
                Range(it)
            else
                null
        }
    }

    fun isDisposed(): Boolean? {
        val result = invoke(JSCODE.IS_DISPOSED)
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
            JSCODE.FIND_MATCHES,
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
            execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", $searchOnlyEditableRange, $isRegex, $matchCase, null, $captureMatches, $limit)")
        else
            execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", $searchOnlyEditableRange, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limit)")

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
                JSCODE.FIND_MATCHES,
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
                execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
            else
                execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
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
                JSCODE.FIND_MATCHES,
                searchString,
                searchScope.targetObject,
                isRegex,
                matchCase,
                wordSeparators,
                captureMatches
            )
        } else {
            if (wordSeparators == null)
                execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
            else
                execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
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
            execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
        else
            execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
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
            JSCODE.FIND_MATCHES,
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
            execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, null, $captureMatches, $limitResultCount)")
        else
            execute("this.${JSCODE.FIND_MATCHES}(\"$searchString\", ${JSON.toJSONString(searchScope)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches, $limitResultCount)")
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
            JSCODE.FIND_MATCHES,
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
            invoke(JSCODE.FIND_NEXT_MATCH, searchStart.targetObject, isRegex, matchCase, wordSeparators, captureMatches)
        } else {
            if (wordSeparators == null)
                execute("this.${JSCODE.FIND_NEXT_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, null, $captureMatches)")
            else
                execute("this.${JSCODE.FIND_NEXT_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches)")

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
            invoke(JSCODE.FIND_PREVIOUS_MATCH, searchStart.targetObject, isRegex, matchCase, wordSeparators, captureMatches)
        } else {
            if (wordSeparators == null)
                execute("this.${JSCODE.FIND_PREVIOUS_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, null, $captureMatches)")
            else
                execute("this.${JSCODE.FIND_PREVIOUS_MATCH}(\"$searchString\", ${JSON.toJSONString(searchStart)}, $isRegex, $matchCase, \"$wordSeparators\", $captureMatches)")

        }?.let {
            if (it is JSObject)
                null
            else
                null
        }
    }

    fun getWordAtPosition(position: IPosition): WordAtPosition? {
        val r = if (position is JsInterfaceObject) {
            invoke(JSCODE.GET_WORD_AT_POSITION, position.targetObject)
        } else {
            execute("this.${JSCODE.GET_WORD_AT_POSITION}(${JSON.toJSONString(position)})")
        } as? JSObject
        return r?.let { WordAtPosition(it) }
    }

    fun getWordUntilPosition(position: IPosition): WordAtPosition? {
        val r = if (position is JsInterfaceObject) {
            invoke(JSCODE.GET_WORD_UNTIL_POSITION, position.targetObject)
        } else {
            execute("this.${JSCODE.GET_WORD_UNTIL_POSITION}(${JSON.toJSONString(position)})")
        } as? JSObject
        return r?.let { WordAtPosition(it) }
    }

    fun normalizeIndentation(str: String): String? {
        return invoke(JSCODE.NORMALIZE_INDENTATION, str) as? String
    }

    fun updateOptions(newOpts: ITextModelUpdateOptions): Boolean {
        return execute("this.${JSCODE.UPDATE_OPTIONS}(${JSON.toJSONString(newOpts)})") == true
    }

    fun detectIndentation(defaultInsertSpaces: Boolean, defaultTabSize: Int): Boolean {
        return invoke(JSCODE.DETECT_INDENTATION, defaultInsertSpaces, defaultTabSize) == true
    }

    fun pushStackElement(): Boolean {
        return invoke(JSCODE.PUSH_STACK_ELEMENT) == true

    }

    fun popStackElement(): Boolean {
        return invoke(JSCODE.POP_STACK_ELEMENT) == true
    }

    fun pushEditOperations(
        beforeCursorState: Array<ISelection>?,
        editOperations: Array<IIdentifiedSingleEditOperation>,
        cursorStateComputer: ICursorStateComputer
    ): Array<Selection>? {
        TODO()
    }

    fun pushEOL(eol: EndOfLineSequence): Boolean {
        return invoke(JSCODE.PUSH_EOL) == true
    }


    fun applyEdits(
        operations: Array<IIdentifiedSingleEditOperation>,
        computeUndoEdits: Boolean
    ): JsArray? /* null | IValidEditOperation */ {
        return execute("this.${JSCODE.APPLY_EDITS}(${JSON.toJSONString(operations)}, $computeUndoEdits)")?.let {
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
        return invoke(JSCODE.APPLY_EDITS, operations.source, computeUndoEdits)?.let {
            if (it is JSObject)
                JsArray(it)
            else
                null
        }
    }

    fun setEOL(eol: Int): Boolean {
        return invoke(JSCODE.SET_EOL, eol) == true

    }

    fun dispose(): Boolean {
        return invoke(JSCODE.DISPOSE) == true
    }

    fun isAttachedToEditor(): Boolean {
        return invoke(JSCODE.IS_ATTACHED_TO_EDITOR) == true

    }
    /////////////////////////////////////////////////////////////
}