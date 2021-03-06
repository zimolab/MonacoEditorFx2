package com.zimolab.monacofx.monaco.editor

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zimolab.jsobject.annotations.JsInterfaceObject
import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.jsbase.JsBridge
import com.zimolab.monacofx.jsbase.execute
import com.zimolab.monacofx.jsbase.invoke
import com.zimolab.monacofx.monaco.*
import com.zimolab.monacofx.monaco.Globals.JS_EDITOR_NAMESPACE
import com.zimolab.monacofx.monaco.Globals.JS_EXCEPTION
import com.zimolab.monacofx.monaco.editor.enums.RevealPosition
import com.zimolab.monacofx.monaco.editor.event.EventBridge
import com.zimolab.monacofx.monaco.editor.event.cursor.CursorPositionChangedEvent
import com.zimolab.monacofx.monaco.editor.event.cursor.CursorSelectionChangedEvent
import com.zimolab.monacofx.monaco.editor.event.editor.EditorEvents
import com.zimolab.monacofx.monaco.editor.event.keyboard.KeyBoardEvent
import com.zimolab.monacofx.monaco.editor.event.miscellaneous.ConfigurationChangedEvent
import com.zimolab.monacofx.monaco.editor.event.miscellaneous.ContentSizeChangedEvent
import com.zimolab.monacofx.monaco.editor.event.miscellaneous.EditorLayoutInfo
import com.zimolab.monacofx.monaco.editor.event.miscellaneous.PasteEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.ModelContentChangedEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.ModelLanguageChangedEvent
import com.zimolab.monacofx.monaco.editor.event.textmodel.ModelOptionsChangedEvent
import com.zimolab.monacofx.monaco.editor.event.mouse.EditorMouseEvent
import com.zimolab.monacofx.monaco.editor.event.mouse.MouseTarget
import com.zimolab.monacofx.monaco.editor.event.scroll.ScrollEvent
import com.zimolab.monacofx.monaco.editor.options.*
import com.zimolab.monacofx.monaco.editor.textmodel.TextModel
import com.zimolab.monacofx.monaco.languages.ILanguageExtensionPoint
import javafx.scene.web.WebEngine
import netscape.javascript.JSObject

class MonacoEditor(val webEngine: WebEngine, val monacoFx: MonacoEditorFx) : JsBridge {
    companion object {
        const val NAME_IN_JS = "javaEditorFx"
        const val DESCRIPTION = ""
        const val JS_EDITOR = "${JS_EDITOR_NAMESPACE}.editor"
        const val JS_INVOKE_EXCEPTION = "JSInvokeException"
        val ERROR_UNEXPECTED_RETURN_VALUE = Throwable("JS invoke did not return a value as expected")
    }

    private var jsEditor: JSObject? = null
    private val addedActions: MutableMap<String, IActionDescriptor> by lazy { mutableMapOf() }
    private val addedCommands: MutableMap<String, ICommandHandler> by lazy { mutableMapOf() }
    private val editorEvents: MutableMap<Int, (eventId: Int, e: Any?) -> Any?> by lazy {
        mutableMapOf()
    }

    private var eventBridge: EventBridge? = null

    object BuiltinThemes {
        const val vs = "vs"
        const val vsDark = "vs-dark"
        const val hcBlack = "hc-black"
    }

    var textModel: TextModel? = null


    override fun getJavascriptName() = NAME_IN_JS
    override fun getDescription() = DESCRIPTION

    ////////////////////////////////////////JS??????/////////////////////////////////////////////////////////////////////////////
    fun onCommand(commandId: String) {
        if (jsEditor != null && commandId in addedCommands.keys) {
            addedCommands[commandId]?.onCommand(commandId)
        }
    }

    fun onActionRun(actionId: String) {
        if (actionId in addedActions)
            addedActions[actionId]?.onRun(actionId)
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////??????API////////////////////////////////////////////////////////////////////////////
    fun onJsEditorReady(jsEditor: JSObject?) {
        this.jsEditor = jsEditor
        eventBridge = if (this.jsEditor != null) {
            EventBridge(this.jsEditor!!)
        } else {
            null
        }
    }

    fun isReady(): Boolean {
        return invoke(JsAPIs.Editor.IS_READY) == true
    }

    /**
     * ??????Editor??????
     * @param createOptions IStandaloneEditorConstructionOptions?
     * @return Boolean
     */
    fun create(createOptions: IStandaloneEditorConstructionOptions?): Boolean {
        val result = execute("$JS_EDITOR.${JsAPIs.Editor.CREATE}(${JSON.toJSONString(createOptions)})") == true
        if (result) {
            // ???????????????????????????execute()???????????????invoke()???????????????onJsEditorReady()?????????????????????jsEditor?????????null
            val jsModel = execute("$JS_EDITOR.${JsAPIs.Editor.GET_MODEL}()")
            if (jsModel is JSObject) {
                textModel = TextModel(jsModel, this)
            } else {
                return false
            }
        }
        return result
    }

    /**
     * ??????????????????
     */
    fun autoLayout(): Boolean {
        return invoke(JsAPIs.Editor.AUTO_LAYOUT) == true
    }

    /**
     *
     * @return CodeEditorViewState?
     */
    fun saveViewState(): CodeEditorViewState? {
        return when (val r = invoke(JsAPIs.Editor.SAVE_VIEW_STATE)) {
            is JSObject -> CodeEditorViewState(r)
            else -> null
        }
    }

    /**
     *
     * @param state CodeEditorViewState
     * @return Boolean
     */
    fun restoreViewState(state: CodeEditorViewState): Boolean {
        return invoke(JsAPIs.Editor.RESTORE_VIEW_STATE, state.targetObject) == true
    }

    /**
     *
     * @return Boolean
     */
    fun hasWidgetFocus(): Boolean {
        return invoke(JsAPIs.Editor.HAS_WIDGET_FOCUS) == true
    }

    /**
     *
     * @return Boolean
     */
    fun hasTextFocus(): Boolean {
        return invoke(JsAPIs.Editor.HAS_TEXT_FOCUS) == true
    }

    /**
     *
     * @return Int?
     */
    fun getContentWidth(): Int? {
        return invoke(JsAPIs.Editor.GET_CONTENT_WIDTH) as? Int
    }

    /**
     *
     * @return Int?
     */
    fun getContentHeight(): Int? {
        return invoke(JsAPIs.Editor.GET_CONTENT_HEIGHT) as? Int
    }

    /**
     * ??????????????????
     * @param descriptor IActionDescriptor
     * @return Boolean
     */
    fun addAction(descriptor: IActionDescriptor): Boolean {
        if (jsEditor == null || descriptor.id in addedActions.keys)
            return false
        val descriptorJson = JSON.toJSONString(descriptor)
        return execute("$JS_EDITOR.${JsAPIs.Editor.ADD_ACTION}($descriptorJson)")?.let { executeReturn ->
            if (executeReturn == true) {
                this.addedActions[descriptor.id] = descriptor
                true
            } else {
                false
            }
        } == true
    }

    /**
     * ??????????????????
     * @param id String
     * @param label String
     * @param contextMenuGroupId String?
     * @param precondition String?
     * @param keybindings Array<Int>?
     * @param keybindingContext String?
     * @param contextMenuOrder Float?
     * @param run Function1<String, Unit>
     * @return IActionDescriptor
     */
    fun createAction(
        id: String,
        label: String,
        contextMenuGroupId: String?,
        precondition: String? = null,
        keybindings: Array<Int>? = null,
        keybindingContext: String? = null,
        contextMenuOrder: Float? = null,
        run: (String) -> Unit
    ): IActionDescriptor {
        return object : IActionDescriptor(
            id = id,
            label = label,
            contextMenuGroupId = contextMenuGroupId,
            precondition = precondition,
            keybindings = keybindings,
            keybindingContext = keybindingContext,
            contextMenuOrder = contextMenuOrder,
            editor = this
        ) {
            override fun onRun(actionId: String) {
                run(actionId)
            }
        }
    }

    /**
     * ???????????????????????????
     * @param id String
     * @param label String
     * @param contextMenuGroupId String?
     * @param precondition String?
     * @param keybindings Array<Int>?
     * @param keybindingContext String?
     * @param contextMenuOrder Float?
     * @param run Function1<String, Unit>
     * @return IActionDescriptor?
     */
    fun addAction(
        id: String,
        label: String,
        contextMenuGroupId: String?,
        precondition: String? = null,
        keybindings: Array<Int>? = null,
        keybindingContext: String? = null,
        contextMenuOrder: Float? = null,
        run: (String) -> Unit
    ): IActionDescriptor? {
        val action = createAction(
            id = id,
            label = label,
            contextMenuGroupId = contextMenuGroupId,
            precondition = precondition,
            keybindings = keybindings,
            keybindingContext = keybindingContext,
            contextMenuOrder = contextMenuOrder,
            run = run
        )
        return if (addAction(action))
            action
        else
            null
    }

    /**
     * ??????????????????????????????
     * @param actionId String
     * @return Boolean
     */
    fun removeAction(actionId: String): Boolean {
        return invoke(JsAPIs.Editor.REMOVE_ACTION, actionId)?.let { invokeReturn ->
            if (invokeReturn == true) {
                if (actionId in addedActions) {
                    addedActions.remove(actionId)
                }
                true
            } else {
                false
            }
        } == true
    }

    /**
     * ??????????????????????????????
     * @param descriptor IActionDescriptor
     * @return Boolean
     */
    fun removeAction(descriptor: IActionDescriptor) = removeAction(descriptor.id)

    /**
     * ??????????????????
     * @param keybinding Int
     * @param handler ICommandHandler
     * @param context String?
     * @return Boolean
     */
    fun addCommand(keybinding: Int, handler: ICommandHandler, context: String?): Boolean {
        val result = if (context == null) {
            invoke(JsAPIs.Editor.ADD_COMMAND, keybinding, handler.id)
        } else {
            invoke(JsAPIs.Editor.ADD_COMMAND, keybinding, handler.id, context)
        }
        if (result == true) {
            addedCommands[handler.id] = handler
            return true
        }
        return false
    }

    fun updateOptions(newOptions: IStandaloneEditorConstructionOptions): Boolean {
        if (jsEditor == null)
            return false
        return execute("$JS_EDITOR.${JsAPIs.Editor.UPDATE_OPTIONS}(${JSON.toJSONString(newOptions)})") == true
    }

    /**
     * ???????????????????????????
     * @return String?
     */
    fun getText(): String? {
        return invoke(JsAPIs.Editor.GET_TEXT)?.let { invokeReturn ->
            if (invokeReturn is String)
                invokeReturn
            else
                null
        }
    }

    /**
     * ???????????????????????????
     * @param text String
     * @return Boolean
     */
    fun setText(text: String): Boolean {
        return invoke(JsAPIs.Editor.SET_TEXT, text) == true
    }

    /**
     * ??????????????????????????????
     * @return Boolean
     */
    fun focus(): Boolean = invoke(JsAPIs.Editor.FOCUS) == true

    /**
     *
     * @return Boolean
     */
    fun pushUndoStop(): Boolean {
        return invoke(JsAPIs.Editor.PUSH_UNDO_STOP) == true
    }

    /**
     *
     * @return Boolean
     */
    fun popUndoStop(): Boolean {
        return invoke(JsAPIs.Editor.POP_UNDO_STOP) == true
    }

    /**
     *
     * @return EditorLayoutInfo?
     */
    fun getLayoutInfo(): EditorLayoutInfo? {
        return when (val r = invoke(JsAPIs.Editor.GET_LAYOUT_INFO)) {
            is JSObject -> EditorLayoutInfo(r)
            else -> null
        }
    }

    /**
     *
     * @param lineNumber Int
     * @param column Int
     * @return Int?
     */
    fun getOffsetForColumn(lineNumber: Int, column: Int): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_OFFSET_FOR_COLUMN)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @param forceRedraw Boolean
     * @return Boolean
     */
    fun render(forceRedraw: Boolean): Boolean {
        return invoke(JsAPIs.Editor.RENDER, forceRedraw) == true
    }

    /**
     *
     * @param clientX Int
     * @param clientY Int
     * @return MouseTarget?
     */
    fun getTargetAtClientPoint(clientX: Int, clientY: Int): MouseTarget? {
        return when (val r = invoke(JsAPIs.Editor.GET_TARGET_AT_CLIENT_POINT)) {
            is JSObject -> MouseTarget(r)
            else -> null
        }
    }

    /**
     *
     * @param position IPosition
     * @return Position
     */
    fun getScrolledVisiblePosition(position: IPosition): Position? {
        return if (position is JsInterfaceObject) {
            invoke(JsAPIs.Editor.GET_SCROLLED_VISIBLE_POSITION, position.targetObject)
        } else {
            execute("$JS_EDITOR.${JsAPIs.Editor.GET_SCROLLED_VISIBLE_POSITION}(${JSON.toJSONString(position)})")
        }?.let {
            if (it is JSObject)
                Position(it)
            else
                null
        }
    }

    /**
     *
     * @return JsArray?
     */
    fun getVisibleRanges(): JsArray? {
        return when (val r = invoke(JsAPIs.Editor.GET_VISIBLE_RANGES)) {
            is JSObject -> JsArray(r)
            else -> null
        }
    }

    /**
     *
     * @param lineNumber Int
     * @return Int?
     */
    fun getTopForLineNumber(lineNumber: Int): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_TOP_FOR_LINE_NUMBER, lineNumber)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @param lineNumber Int
     * @param column Int
     * @return Int?
     */
    fun getTopForPosition(lineNumber: Int, column: Int): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_TOP_FOR_POSITION, lineNumber, column)) {
            is Int -> r
            else -> null
        }
    }

    /**
     * ??????????????????
     * @return IComputedEditorOptions?
     */
    fun getOptions(): IComputedEditorOptions? {
        return invoke(JsAPIs.Editor.GET_OPTIONS)?.let { invokeReturn ->
            ensureJSONObject(invokeReturn) { jsonObj ->
                ensureJSONArray(IComputedEditorOptions.KEY, jsonObj) { jsArray ->
                    IComputedEditorOptions(jsArray)
                }
            }
        }
    }

    /**
     * ??????id??????Action
     * @param id String
     * @return EditorAction
     */
    fun getAction(id: String): EditorAction? {
        return invoke(JsAPIs.Editor.GET_ACTION, id)?.let { invokeReturn ->
            if (invokeReturn is JSObject)
                EditorAction(invokeReturn)
            else
                null
        }
    }

    /**
     * ???????????????
     * @param id Int
     * @return T?
     */
    inline fun <reified T> getOption(id: Int): T? {
        return invoke(JsAPIs.Editor.GET_OPTION, id)?.let { invokeReturn ->
            ensureTypeT<T>(invokeReturn) {
                JSONObject.parseObject(it as String, T::class.java)
            }
        }
    }

    /**
     * ??????????????????
     * @return String?
     */
    fun getCurrentLanguage(): String? {
        return invoke(JsAPIs.Editor.GET_CURRENT_LANGUAGE)?.let { invokeReturn ->
            if (invokeReturn is String)
                invokeReturn
            else
                null
        }
    }

    /**
     * ??????????????????
     * @return Boolean
     */
    fun setCurrentLanguage(language: String): Boolean {
        return invoke(JsAPIs.Editor.SET_CURRENT_LANGUAGE, language) == true
    }

    /**
     * ??????????????????????????????
     * @return List<ILanguageExtensionPoint>?
     */
    fun getLanguages(): List<ILanguageExtensionPoint>? {
        return invoke(JsAPIs.Editor.GET_LANGUAGES)?.let { invokeReturn ->
            ensureString(invokeReturn) { str ->
                ensureTypeT<List<ILanguageExtensionPoint>>(str) {
                    val l = JSON.parseArray(it as String, ILanguageExtensionPoint::class.java)
                    l
                }
            }
        }
    }

    /**
     * ??????????????????
     * @param newScrollLeft Int
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollLeft(newScrollLeft: Int, scrollType: Int?): Boolean {
        return if (scrollType == null) {
            invoke(JsAPIs.Editor.SET_SCROLL_LEFT, newScrollLeft)
        } else {
            invoke(JsAPIs.Editor.SET_SCROLL_LEFT, newScrollLeft, scrollType)
        } == true
    }

    /**
     * ??????????????????
     * @param newScrollTop Int
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollTop(newScrollTop: Int, scrollType: Int?): Boolean {
        return if (scrollType == null) {
            invoke(JsAPIs.Editor.SET_SCROLL_TOP, newScrollTop)
        } else {
            invoke(JsAPIs.Editor.SET_SCROLL_TOP, newScrollTop, scrollType)
        } == true
    }

    /**
     * ??????????????????
     * @param position INewScrollPosition
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollPosition(position: INewScrollPosition, scrollType: Int?): Boolean {
        return execute("$JS_EDITOR.${JsAPIs.Editor.SET_SCROLL_POSITION}(${JSON.toJSONString(position)}, $scrollType)") == true
    }

    /**
     * ??????????????????
     * @param positionLeft Int
     * @param positionTop Int
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollPositionXY(positionLeft: Int, positionTop: Int, scrollType: Int?): Boolean {
        return if (scrollType == null) {
            invoke(JsAPIs.Editor.SET_SCROLL_POSITION_XY, positionLeft, positionTop)
        } else {
            invoke(JsAPIs.Editor.SET_SCROLL_POSITION_XY, positionLeft, positionTop, scrollType)
        } == true
    }

    /**
     *
     * @return Int?
     */
    fun getScrollWidth(): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_SCROLL_WIDTH)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getScrollLeft(): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_SCROLL_LEFT)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getScrollHeight(): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_SCROLL_HEIGHT)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getScrollTop(): Int? {
        return when (val r = invoke(JsAPIs.Editor.GET_SCROLL_TOP)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @param selections Array<ISelection>
     * @return Boolean
     */
    fun setSelections(selections: Array<ISelection>): Boolean {
        return execute("$JS_EDITOR.${JsAPIs.Editor.SET_SELECTIONS}(${JSON.toJSONString(selections)})") == true
    }

    /**
     *
     * @param selections JsArray
     * @return Boolean
     */
    fun setSelections(selections: JsArray): Boolean {
        return invoke(JsAPIs.Editor.SET_SELECTIONS, selections.source) == true
    }

    /**
     *
     * @param selection IRange
     * @return Boolean
     */
    fun setSelection(selection: IRange): Boolean {
        return if (selection is JsInterfaceObject) {
            invoke(JsAPIs.Editor.SET_SELECTION, selection.targetObject) == true
        } else {
            execute("$JS_EDITOR.${JsAPIs.Editor.SET_SELECTION}(${json(selection)})") == true
        }
    }

    /**
     *
     * @param selection ISelection
     * @return Boolean
     */
    fun setSelection(selection: ISelection): Boolean {
        return if (selection is JsInterfaceObject) {
            invoke(JsAPIs.Editor.SET_SELECTION, selection.targetObject) == true
        } else {
            execute("$JS_EDITOR.${JsAPIs.Editor.SET_SELECTION}(${json(selection)})") == true
        }
    }

    fun getVisibleColumnFromPosition(position: IPosition) {
        if (position is JsInterfaceObject) {
            invoke(JsAPIs.Editor.GET_VISIBLE_COLUMN_FROM_POSITION, position.targetObject)
        } else {
            execute("$JS_EDITOR.${JsAPIs.Editor.GET_VISIBLE_COLUMN_FROM_POSITION}(${json(position)})")
        }
    }

    /**
     *
     * @return Selection?
     */
    fun getSelection(): Selection? {
        return invoke(JsAPIs.Editor.GET_SELECTION)?.let {
            if (it is JSObject)
                Selection(it)
            else
                null
        }
    }

    fun getSelections(): JsArray? {
        return invoke(JsAPIs.Editor.GET_SELECTIONS)?.let {
            if (it is JSObject)
                JsArray(it)
            else
                null
        }
    }

    /**
     *
     * @param column Int
     * @param lineNumber Int
     * @return Boolean
     */
    fun setPosition(column: Int, lineNumber: Int): Boolean {
        return execute("$JS_EDITOR.${JsAPIs.Editor.SET_POSITION}({column: $column, lineNumber: $lineNumber})") == true
    }

    /**
     *
     * @param position IPosition
     * @return Boolean
     */
    fun setPosition(position: IPosition): Boolean {
        return if (position is JsInterfaceObject) {
            invoke(JsAPIs.Editor.SET_SELECTION, position.targetObject) == true
        } else {
            execute("$JS_EDITOR.${JsAPIs.Editor.SET_POSITION}(${json(position)})") == true
        }
    }

    /**
     *
     * @param source String?
     * @param edits Array<IIdentifiedSingleEditOperation>
     * @param endCursorState Array<ISelection>?
     * @return Boolean
     */
    fun executeEdits(
        source: String?,
        edits: Array<IIdentifiedSingleEditOperation>,
        endCursorState: Array<ISelection>?
    ): Boolean {
        return if (endCursorState == null) {
            execute(JsAPIs.Editor.EXECUTE_EDITS.format(source, JSON.toJSONString(edits), "undefined"))
        } else {
            execute(JsAPIs.Editor.EXECUTE_EDITS.format(source, JSON.toJSONString(edits), JSON.toJSONString(endCursorState)))
        } == true
    }

    fun revealLine(revealPosition: RevealPosition, lineNumber: Int, scrollType: Int): Boolean {
        return when (revealPosition) {
            RevealPosition.Default -> invoke(JsAPIs.Editor.REVEAL_LINE, lineNumber, scrollType) == true
            RevealPosition.InCenter -> invoke(JsAPIs.Editor.REVEAL_LINE_IN_CENTER, lineNumber, scrollType) == true
            RevealPosition.InCenterIfOutsideViewport -> invoke(
                JsAPIs.Editor.REVEAL_LINE_IN_CENTER_IF_OUTSIDE_VIEWPORT,
                lineNumber,
                scrollType
            ) == true
            RevealPosition.NearTop -> invoke(JsAPIs.Editor.REVEAL_LINE_NEAR_TOP, lineNumber, scrollType) == true
            else -> false
        }
    }


    fun revealLines(
        revealPosition: RevealPosition,
        startLineNumber: Int,
        endLineNumber: Int,
        scrollType: Int
    ): Boolean {
        val js = when (revealPosition) {
            RevealPosition.Default -> JsAPIs.Editor.REVEAL_LINES
            RevealPosition.InCenter -> JsAPIs.Editor.REVEAL_LINES_IN_CENTER
            RevealPosition.InCenterIfOutsideViewport -> JsAPIs.Editor.REVEAL_LINES_IN_CENTER_IF_OUTSIDE_VIEWPORT
            RevealPosition.NearTop -> JsAPIs.Editor.REVEAL_LINES_NEAR_TOP
            else ->
                return false
        }
        return invoke(js, startLineNumber, endLineNumber, scrollType) == true
    }

    fun revealPosition(revealPosition: RevealPosition, position: IPosition, scrollType: Int): Boolean {
        val js = when (revealPosition) {
            RevealPosition.Default -> JsAPIs.Editor.REVEAL_POSITION
            RevealPosition.InCenter -> JsAPIs.Editor.REVEAL_POSITION_IN_CENTER
            RevealPosition.InCenterIfOutsideViewport -> JsAPIs.Editor.REVEAL_POSITION_IN_CENTER_IF_OUTSIDE_VIEWPORT
            RevealPosition.NearTop -> JsAPIs.Editor.REVEAL_POSITION_NEAR_TOP
            else ->
                return false
        }
        return if (position is JsInterfaceObject) {
            invoke(js, position.targetObject, scrollType)
        } else {
            execute("$JS_EDITOR.$js(${JSON.toJSONString(position)}, $scrollType)")
        } == true
    }

    fun revealRange(revealPosition: RevealPosition, range: IRange, scrollType: Int): Boolean {
        val js = when (revealPosition) {
            RevealPosition.Default -> JsAPIs.Editor.REVEAL_RANGE
            RevealPosition.InCenter -> JsAPIs.Editor.REVEAL_RANGE_IN_CENTER
            RevealPosition.InCenterIfOutsideViewport -> JsAPIs.Editor.REVEAL_RANGE_IN_CENTER_IF_OUTSIDE_VIEWPORT
            RevealPosition.NearTopIfOutsideViewport -> JsAPIs.Editor.REVEAL_RANGE_NEAR_TOP_IF_OUTSIDE_VIEWPORT
            RevealPosition.AtTop -> JsAPIs.Editor.REVEAL_RANGE_AT_TOP
            else ->
                return false
        }
        return if (range is JsInterfaceObject) {
            invoke(js, range.targetObject, scrollType)
        } else {
            execute("$JS_EDITOR.$js(${JSON.toJSONString(range)}, $scrollType)")
        } == true
    }

    /**
     * ????????????????????????
     * @param source String
     * @param handlerId String
     * @param payload Any?
     */
    fun trigger(source: String?, handlerId: String, payload: Any?): Boolean {
        return (if (payload == null)
            invoke(JsAPIs.Editor.TRIGGER, source, handlerId)
        else
            invoke(JsAPIs.Editor.TRIGGER, source, handlerId, payload)) == true
    }

    fun setTheme(themeName: String): Boolean {
        return invoke(JsAPIs.Editor.SET_THEME, themeName) == true
    }

    fun getTheme(): String? {
        return invoke(JsAPIs.Editor.GET_THEME) as? String
    }

    fun getBuiltinThemes(): JsArray? {
        return invoke(JsAPIs.Editor.GET_BUILTIN_THEMES)?.let {
            if (it is JSObject)
                JsArray(it)
            else
                null
        }
    }

    fun defineTheme(themeName: String, themeData: IStandaloneThemeData): Boolean {
        return execute("""$JS_EDITOR.${JsAPIs.Editor.DEFINE_THEME}("$themeName", ${JSON.toJSONString(themeData)})""") == true
    }

    fun dispose(): Boolean {
        textModel?.let {
            if (it.isDisposed() == false)
                it.dispose()
        }
        textModel = null
        editorEvents.forEach { (eventId, _) ->
            eventBridge?.unlisten(eventId)
        }
        return invoke(JsAPIs.Editor.DISPOSE) == true
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////?????????????????????///////////////////////////////////////////////////////////////////////////////
    fun execute(jsCode: String): Any? {
        val result = webEngine.execute(jsCode)
        if (result is Throwable) {
            monacoFx.internalErrorOccurs(JS_EXCEPTION, result)
            return null
        }
        return result
    }

    fun invoke(methodName: String, vararg args: Any?): Any? {
        if (jsEditor == null)
            return null
        val result = jsEditor!!.invoke(methodName, *args)
        if (result is Throwable) {
            monacoFx.internalErrorOccurs(JS_EXCEPTION, result)
            return null
        }
        return result
    }

    private fun json(obj: Any): String {
        return JSON.toJSONString(obj)
    }

    private inline fun <reified T> ensureString(
        input: Any,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: (String) -> T?
    ): T? {
        return if (input is String) {
            block(input)
        } else {
            monacoFx.internalErrorOccurs(error.first, error.second)
            null
        }
    }

    private inline fun <reified T> ensureJSONObject(
        input: String,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: (JSONObject) -> T?
    ): T? {
        return try {
            val jsonObject = JSON.parse(input) as JSONObject
            block(jsonObject)
        } catch (e: Throwable) {
            monacoFx.internalErrorOccurs(error.first, error.second)
            null
        }
    }

    private inline fun <reified T> ensureJSONObject(
        input: Any,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: (JSONObject) -> T?
    ): T? {
        return ensureString(input) { inputString ->
            ensureJSONObject(inputString, error, block)
        }
    }

    private inline fun <reified T> ensureJSONArray(
        key: String,
        jsonObj: JSONObject,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: (JSONArray) -> T?
    ): T? {
        return if (key in jsonObj && jsonObj[key] is JSONArray) {
            block(jsonObj[key] as JSONArray)
        } else {
            monacoFx.internalErrorOccurs(error.first, error.second)
            null
        }
    }

    private inline fun <reified T> ensureTypeT(
        jsonObj: JSONObject,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: ((jsonObj: JSONObject) -> T?) = { jsonObj.toJavaObject(T::class.java) }
    ): T? {
        return try {
            block(jsonObj)
        } catch (e: Throwable) {
            monacoFx.internalErrorOccurs(error.first, error.second)
            null
        }
    }

    inline fun <reified T> ensureTypeT(
        obj: Any,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: ((anyObject: Any) -> T?)
    ): T? {
        return try {
            block(obj)
        } catch (e: Throwable) {
            monacoFx.internalErrorOccurs(error.first, error.second)
            null
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////?????????JS???Kotlin?????????????????????////////////////////////////////////////////////////////////////
    fun onEditorEvent(eventId: Int, e: Any?) {
        if (eventId in editorEvents)
            editorEvents[eventId]?.invoke(eventId, e)
    }

    fun listen(eventId: Int, callback: (eventId: Int, e: Any?) -> Any?): Boolean {
        return eventBridge?.let { eventBridge ->
            if (eventBridge.listen(eventId)) {
                editorEvents[eventId] = callback
                true
            } else {
                false
            }
        } == true
    }

    fun unlisten(eventId: Int): Boolean {
        if (eventId in editorEvents)
            editorEvents.remove(eventId)
        return eventBridge?.unlisten(eventId) == true
    }

    fun isListened(eventId: Int): Boolean {
        return eventBridge?.isListened(eventId) == true
    }

    fun onMouseDown(listener: ((eventId: Int, event: EditorMouseEvent) -> Unit)?): Boolean {
        if (listener == null) {
            return unlisten(EditorEvents.onMouseDown)
        }

        return listen(EditorEvents.onMouseDown) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onMouseUp(listener: ((eventId: Int, event: EditorMouseEvent) -> Unit)?): Boolean {
        if (listener == null) {

            return unlisten(EditorEvents.onMouseUp)
        }

        return listen(EditorEvents.onMouseUp) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onMouseMove(listener: ((eventId: Int, event: EditorMouseEvent) -> Unit)?): Boolean {
        if (listener == null) {
            return unlisten(EditorEvents.onMouseMove)
        }
        return listen(EditorEvents.onMouseMove) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onMouseLeave(listener: ((eventId: Int, event: EditorMouseEvent) -> Unit)?): Boolean {
        if (listener == null) {
            return unlisten(EditorEvents.onMouseLeave)
        }
        return listen(EditorEvents.onMouseLeave) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onKeyUp(listener: ((eventId: Int, event: KeyBoardEvent) -> Unit)?): Boolean {
        if (listener == null) {
            return unlisten(EditorEvents.onKeyUp)
        }
        return listen(EditorEvents.onKeyUp) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, KeyBoardEvent(e))
            }
        }
    }

    fun onKeyDown(listener: ((eventId: Int, event: KeyBoardEvent) -> Unit)?): Boolean {
        if (listener == null) {
            return unlisten(EditorEvents.onKeyDown)

        }
        return listen(EditorEvents.onKeyDown) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, KeyBoardEvent(e))
            }
        }
    }

    fun onDidScrollChange(listener: ((eventId: Int, event: ScrollEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidScrollChange)
            return
        }
        listen(EditorEvents.onDidScrollChange) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, ScrollEvent(e))
            }
        }
    }

    fun onDidPaste(listener: ((eventId: Int, event: PasteEvent) -> Unit)?): Boolean {
        if (listener == null) {
            return unlisten(EditorEvents.onDidPaste)

        }
        return listen(EditorEvents.onDidPaste) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, PasteEvent(e))
            }
        }
    }

    fun onDidLayoutChange(listener: ((eventId: Int, event: EditorLayoutInfo) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidLayoutChange)
            return
        }
        listen(EditorEvents.onDidLayoutChange) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorLayoutInfo(e))
            }
        }
    }

    fun onDidFocusEditorWidget(listener: ((eventId: Int, event: Any?) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidFocusEditorWidget)
            return
        }
        listen(EditorEvents.onDidFocusEditorWidget, listener)
    }

    fun onDidFocusEditorText(listener: ((eventId: Int, event: Any?) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidFocusEditorText)
            return
        }
        listen(EditorEvents.onDidFocusEditorText, listener)
    }

    fun onDidBlurEditorText(listener: ((eventId: Int, event: Any?) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidBlurEditorText)
            return
        }
        listen(EditorEvents.onDidBlurEditorText, listener)
    }

    fun onDidBlurEditorWidget(listener: ((eventId: Int, event: Any?) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidBlurEditorWidget)
            return
        }
        listen(EditorEvents.onDidBlurEditorWidget, listener)
    }

    fun onContextMenu(listener: ((eventId: Int, event: EditorMouseEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onContextMenu)
            return
        }
        listen(EditorEvents.onContextMenu) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onDidAttemptReadOnlyEdit(listener: ((eventId: Int) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidAttemptReadOnlyEdit)
            return
        }
        listen(EditorEvents.onDidAttemptReadOnlyEdit) { eventId: Int, _: Any? ->
            listener(eventId)
        }
    }

    fun onDidChangeConfiguration(listener: ((eventId: Int, event: ConfigurationChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeConfiguration)
            return
        }
        listen(EditorEvents.onDidChangeConfiguration) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ConfigurationChangedEvent(e))
        }
    }

    fun onDidChangeCursorPosition(listener: ((eventId: Int, event: CursorPositionChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeCursorPosition)
            return
        }
        listen(EditorEvents.onDidChangeCursorPosition) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, CursorPositionChangedEvent(e))
        }
    }

    fun onDidChangeCursorSelection(listener: ((eventId: Int, event: CursorSelectionChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeCursorSelection)
            return
        }
        listen(EditorEvents.onDidChangeCursorSelection) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, CursorSelectionChangedEvent(e))
        }
    }

    fun onDidChangeModelContent(listener: ((eventId: Int, event: ModelContentChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeModelContent)
            return
        }
        listen(EditorEvents.onDidChangeModelContent) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ModelContentChangedEvent(e))
        }
    }

    fun onDidChangeModelLanguage(listener: ((eventId: Int, event: ModelLanguageChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeModelLanguage)
            return
        }
        listen(EditorEvents.onDidChangeModelLanguage) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ModelLanguageChangedEvent(e))
        }
    }

    fun onDidChangeModelOptions(listener: ((eventId: Int, event: ModelOptionsChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeModelOptions)
            return
        }
        listen(EditorEvents.onDidChangeModelOptions) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ModelOptionsChangedEvent(e))
        }
    }

    fun onDidContentSizeChange(listener: ((eventId: Int, event: ContentSizeChangedEvent) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidContentSizeChange)
            return
        }
        listen(EditorEvents.onDidContentSizeChange) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ContentSizeChangedEvent(e))
        }
    }

    fun onDidDispose(listener: ((eventId: Int) -> Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidDispose)
            return
        }
        listen(EditorEvents.onDidDispose) { eventId: Int, _: Any? ->
            listener(eventId)
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}