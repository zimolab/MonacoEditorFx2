package com.zimolab.monacofx.monaco.editor

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.jsbase.JsBridge
import com.zimolab.monacofx.jsbase.execute
import com.zimolab.monacofx.jsbase.invoke
import com.zimolab.monacofx.monaco.Globals.JS_EDITOR_NAMESPACE
import com.zimolab.monacofx.monaco.Globals.JS_EXCEPTION
import com.zimolab.monacofx.monaco.IPosition
import com.zimolab.monacofx.monaco.RangeObject
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

class MonacoEditor(val webEngine: WebEngine, val monacoFx: MonacoEditorFx) : JsBridge, IEditorEventProcessor {
    companion object {
        const val NAME_IN_JS_ENV = "javaEditorFx"
        const val DESCRIPTION = ""
        const val JS_EDITOR_ID = "${JS_EDITOR_NAMESPACE}.editor"
        const val JS_INVOKE_EXCEPTION = "JSInvokeException"
        val ERROR_UNEXPECTED_RETURN_VALUE = Throwable("JS invoke did not return a value as expected")
    }

    object JSCODE {
        const val CREATE = "$JS_EDITOR_ID.create(%s);"
        const val IS_READY = "isReady"
        const val GET_LANGUAGES = "getLanguages"
        const val GET_OPTIONS = "getOptions"
        const val GET_OPTION = "getOption"
        const val ADD_ACTION = "$JS_EDITOR_ID.addAction(%s);"
        const val REMOVE_ACTION = "removeAction"
        const val ADD_COMMAND = "addCommand"
        const val UPDATE_OPTIONS = "$JS_EDITOR_ID.updateOptions(%s);"
        const val FOCUS = "focus"
        const val AUTO_LAYOUT = "autoLayout"
        const val SET_TEXT = "setText"
        const val GET_TEXT = "getText"
        const val GET_CURRENT_LANGUAGE = "getCurrentLanguage"
        const val SET_CURRENT_LANGUAGE = "setCurrentLanguage"
        const val SET_SCROLL_LEFT = "setScrollLeft"
        const val SET_SCROLL_TOP = "setScrollTop"
        const val SET_SCROLL_POSITION = "$JS_EDITOR_ID.setScrollPosition(%s, %s);"
        const val SET_SCROLL_POSITION_XY = "setScrollPositionXY"
        const val GET_ACTION = "getAction"
        const val GET_CONTENT_HEIGHT = "getContentHeight"
        const val GET_CONTENT_WIDTH = "getContentWidth"
        const val GET_LAYOUT_INFO = "getLayoutInfo"
        const val GET_LINE_DECORATIONS = "getLineDecorations"
        const val GET_OFFSET_FOR_COLUMN = "getOffsetForColumn"
        const val GET_SCROLL_HEIGHT = "getScrollHeight"
        const val GET_SCROLL_LEFT = "getScrollLeft"
        const val GET_SCROLL_TOP = "getScrollTop"
        const val GET_SCROLL_WIDTH = "getScrollWidth"
        const val GET_SCROLLED_VISIBLE_POSITION = "getScrolledVisiblePosition"
        const val GET_SELECTION = "getSelection"
        const val GET_SELECTIONS = "getSelections"
        const val GET_SUPPORTED_ACTIONS = "getSupportedActions"
        const val GET_TARGET_AT_CLIENT_POINT = "getTargetAtClientPoint"
        const val GET_TOP_FOR_LINE_NUMBER = "getTopForLineNumber"
        const val GET_TOP_FOR_POSITION = "getTopForPosition"
        const val GET_VALUE = "getValue"
        const val GET_VISIBLE_COLUMN_FROM_POSITION = "getVisibleColumnFromPosition"
        const val GET_VISIBLE_RANGES = "getVisibleRanges"
        const val HAS_TEXT_FOCUS = "hasTextFocus"
        const val HAS_WIDGET_FOCUS = "hasWidgetFocus"
        const val LAYOUT_CONTENT_WIDGET = "layoutContentWidget"
        const val LAYOUT_OVERLAY_WIDGET = "layoutOverlayWidget"
        const val POP_UNDO_STOP = "popUndoStop"
        const val PUSH_UNDO_STOP = "pushUndoStop"
        const val RENDER = "render"
        const val RESTORE_VIEW_STATE = "restoreViewState"
        const val REVEAL_LINE = "revealLine"
        const val SAVE_VIEW_STATE = "saveViewState"
        const val REVEAL_LINE_IN_CENTER = "revealLineInCenter"
        const val REVEAL_LINE_IN_CENTER_IF_OUTSIDE_VIEWPORT = "revealLineInCenterIfOutsideViewport"
        const val REVEAL_LINE_NEAR_TOP = "revealLineNearTop"
        const val REVEAL_LINES = "revealLines"
        const val REVEAL_LINES_IN_CENTER = "revealLinesInCenter"
        const val REVEAL_LINES_IN_CENTER_IF_OUTSIDE_VIEWPORT = "revealLinesInCenterIfOutsideViewport"
        const val REVEAL_LINES_NEAR_TOP = "revealLinesNearTop"
        const val REVEAL_POSITION = "revealPosition"
        const val REVEAL_POSITION_IN_CENTER = "revealPositionInCenter"
        const val REVEAL_POSITION_IN_CENTER_IF_OUTSIDE_VIEWPORT = "revealPositionInCenterIfOutsideViewport"
        const val REVEAL_POSITION_NEAR_TOP = "revealPositionNearTop"
        const val REVEAL_RANGE = "revealRange"
        const val REVEAL_RANGE_AT_TOP = "revealRangeAtTop"
        const val REVEAL_RANGE_IN_CENTER = "revealRangeInCenter"
        const val REVEAL_RANGE_IN_CENTER_IF_OUTSIDE_VIEWPORT = "revealRangeInCenterIfOutsideViewport"
        const val REVEAL_RANGE_NEAR_TOP_IF_OUTSIDE_VIEWPORT = "revealRangeNearTopIfOutsideViewport"
        const val SET_POSITION = "$JS_EDITOR_ID.setPosition(%s)"
        const val SET_SELECTION = "$JS_EDITOR_ID.setSelection(%s)"
        const val SET_SELECTIONS = "$JS_EDITOR_ID.setSelections(%s)"
        const val SET_VALUE = "setValue"
        const val TRIGGER = "trigger"
        const val EXECUTE_EDITS = "$JS_EDITOR_ID.executeEdits(%s, %s, %s)"
        const val GET_MODEL = "${JS_EDITOR_ID}.getTextModel()"
    }

    var jsEditor: JSObject? = null
    val addedActions: MutableMap<String, IActionDescriptor> by lazy { mutableMapOf() }
    val addedCommands: MutableMap<String, ICommandHandler> by lazy { mutableMapOf() }
    private val editorEvents: MutableMap<Int, (eventId: Int, e: Any?)->Any?> by lazy {
        mutableMapOf()
    }

    lateinit var textModel: TextModel


    override fun getJavascriptName() = NAME_IN_JS_ENV
    override fun getDescription() = DESCRIPTION

    /****************************JS回调函数***********************************/
    fun onCommand(commandId: String) {
        if (jsEditor != null && commandId in addedCommands.keys) {
            addedCommands[commandId]?.onCommand(commandId)
        }
    }

    fun onActionRun(actionId: String) {
        if (actionId in addedActions)
            addedActions[actionId]?.onRun(actionId)
    }

    fun onEditorEvent(eventId: Int, e: Any?) {
        if (eventId in editorEvents)
            editorEvents[eventId]?.invoke(eventId, e)
    }
    /***********************************************************************/


    fun isReady(): Boolean {
        return invoke(JSCODE.IS_READY) == true
    }


    ///////////////////////////////Editor API///////////////////////////////////////
    /**
     * 创建Editor实例
     * @param createOptions IStandaloneEditorConstructionOptions?
     * @return Boolean
     */
    fun create(createOptions: IStandaloneEditorConstructionOptions?): Boolean {
        val result = execute(JSCODE.CREATE.format(JSON.toJSONString(createOptions))) == true
        if (result) {
            val r = execute(JSCODE.GET_MODEL)
            if (r is JSObject) {
                textModel = TextModel(r, this)
            } else {
                return false
            }
        }
        return result
    }

    /**
     * 自动重新布局
     */
    fun autoLayout(): Boolean {
        return invoke(JSCODE.AUTO_LAYOUT) == true
    }

    /**
     *
     * @return CodeEditorViewState?
     */
    fun saveViewState(): CodeEditorViewState? {
        return when(val r = invoke(JSCODE.SAVE_VIEW_STATE)) {
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
        return invoke(JSCODE.RESTORE_VIEW_STATE, state.targetObject) == true
    }

    /**
     *
     * @return Boolean
     */
    fun hasWidgetFocus(): Boolean {
        return invoke(JSCODE.HAS_WIDGET_FOCUS) == true
    }

    /**
     *
     * @return Boolean
     */
    fun hasTextFocus(): Boolean {
        return invoke(JSCODE.HAS_TEXT_FOCUS) == true
    }

    /**
     *
     * @return Int?
     */
    fun getContentWidth(): Int? {
        return when(val r = invoke(JSCODE.GET_CONTENT_WIDTH)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getContentHeight(): Int? {
        return when(val r = invoke(JSCODE.GET_CONTENT_HEIGHT)) {
            is Int -> r
            else -> null
        }
    }


    /**
     * 添加一个动作
     * @param descriptor IActionDescriptor
     * @return Boolean
     */
    fun addAction(descriptor: IActionDescriptor): Boolean {
        if (jsEditor == null || descriptor.id in addedActions.keys)
            return false
        val descriptorJson = JSON.toJSONString(descriptor)
        val ret = execute(JSCODE.ADD_ACTION.format(descriptorJson))?.let { executeReturn ->
            if (executeReturn == true) {
                this.addedActions[descriptor.id] = descriptor
                executeReturn
            } else {
                null
            }
        }
        return ret == true
    }

    /**
     * 创建一个动作
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
     * 创建并添加一个动作
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
     * 移除一个已添加的动作
     * @param actionId String
     * @return Boolean
     */
    fun removeAction(actionId: String): Boolean {
        return invoke(JSCODE.REMOVE_ACTION, actionId)?.let { invokeReturn ->
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
     * 移除一个已添加的动作
     * @param descriptor IActionDescriptor
     * @return Boolean
     */
    fun removeAction(descriptor: IActionDescriptor) = removeAction(descriptor.id)

    /**
     * 添加一个命令
     * @param keybinding Int
     * @param handler ICommandHandler
     * @param context String?
     * @return Boolean
     */
    fun addCommand(keybinding: Int, handler: ICommandHandler, context: String?): Boolean {
        val result = if (context == null) {
            invoke(JSCODE.ADD_COMMAND, keybinding, handler.id)
        } else {
            invoke(JSCODE.ADD_COMMAND, keybinding, handler.id, context)
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
        return execute(JSCODE.UPDATE_OPTIONS.format(JSON.toJSONString(newOptions))) == true
    }

    /**
     * 获取当前编辑器文本
     * @return String?
     */
    fun getText(): String? {
        return invoke(JSCODE.GET_TEXT)?.let { invokeReturn->
            if (invokeReturn is String)
                invokeReturn
            else
                null
        }
    }

    /**
     * 设置编辑器当前文本
     * @param text String
     * @return Boolean
     */
    fun setText(text: String): Boolean {
        return invoke(JSCODE.SET_TEXT, text) == true
    }

    /**
     * 将焦点设置到编辑器上
     * @return Boolean
     */
    fun focus(): Boolean = invoke(JSCODE.FOCUS) == true

    /**
     *
     * @return Boolean
     */
    fun pushUndoStop(): Boolean {
        return invoke(JSCODE.PUSH_UNDO_STOP) == true
    }

    /**
     *
     * @return Boolean
     */
    fun popUndoStop(): Boolean {
        return invoke(JSCODE.POP_UNDO_STOP) == true
    }

    /**
     *
     * @return EditorLayoutInfo?
     */
    fun getLayoutInfo(): EditorLayoutInfo? {
        return when(val r = invoke(JSCODE.GET_LAYOUT_INFO)) {
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
        return when(val r = invoke(JSCODE.GET_OFFSET_FOR_COLUMN)) {
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
        return invoke(JSCODE.RENDER, forceRedraw) == true
    }

    /**
     *
     * @param clientX Int
     * @param clientY Int
     * @return MouseTarget?
     */
    fun getTargetAtClientPoint(clientX: Int, clientY: Int): MouseTarget? {
        return when(val r = invoke(JSCODE.GET_TARGET_AT_CLIENT_POINT)) {
            is JSObject -> MouseTarget(r)
            else -> null
        }
    }

    /**
     *
     * @param position IPosition
     * @return Any?
     */
    fun getScrolledVisiblePosition(position: IPosition): Any? {
        return when(val r = invoke(JSCODE.GET_TARGET_AT_CLIENT_POINT)) {
            is JSObject -> r
            else -> null
        }
    }

    /**
     *
     * @return JsArray?
     */
    fun getVisibleRanges(): JsArray? {
        return when(val r = invoke(JSCODE.GET_VISIBLE_RANGES)) {
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
        return when(val r = invoke(JSCODE.GET_TOP_FOR_LINE_NUMBER, lineNumber)) {
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
        return when(val r = invoke(JSCODE.GET_TOP_FOR_POSITION, lineNumber, column)) {
            is Int -> r
            else -> null
        }
    }

    /**
     * 获取当前设置
     * @return IComputedEditorOptions?
     */
    fun getOptions(): IComputedEditorOptions? {
        return invoke(JSCODE.GET_OPTIONS)?.let { invokeReturn ->
            ensureJSONObject(invokeReturn) { jsonObj ->
                ensureJSONArray(IComputedEditorOptions.KEY, jsonObj) { jsArray ->
                    IComputedEditorOptions(jsArray)
                }
            }
        }
    }

    /**
     * 通过id获取Action
     * @param id String
     * @return EditorAction
     */
    fun getAction(id: String): EditorAction? {
        return invoke(JSCODE.GET_ACTION, id)?.let { invokeReturn->
            if (invokeReturn is JSObject)
                EditorAction(invokeReturn)
            else
                null
        }
    }

    /**
     * 获取配置项
     * @param id Int
     * @return T?
     */
    inline fun <reified T> getOption(id: Int): T? {
        return invoke(JSCODE.GET_OPTION, id)?.let { invokeReturn ->
            ensureTypeT<T>(invokeReturn) {
                JSONObject.parseObject(it as String, T::class.java)
            }
        }
    }

    /**
     * 获取当前语言
     * @return String?
     */
    fun getCurrentLanguage(): String? {
        return invoke(JSCODE.GET_CURRENT_LANGUAGE)?.let { invokeReturn ->
            if (invokeReturn is String)
                invokeReturn
            else
                null
        }
    }

    /**
     * 设置当前语言
     * @return Boolean
     */
    fun setCurrentLanguage(): Boolean {
        return invoke(JSCODE.SET_CURRENT_LANGUAGE) == true
    }

    /**
     * 获取编辑器支持的语言
     * @return List<ILanguageExtensionPoint>?
     */
    fun getLanguages(): List<ILanguageExtensionPoint>? {
        return invoke(JSCODE.GET_LANGUAGES)?.let { invokeReturn ->
            ensureString(invokeReturn) { str ->
                ensureTypeT<List<ILanguageExtensionPoint>>(str) {
                    val l = JSON.parseArray(it as String, ILanguageExtensionPoint::class.java)
                    l
                }
            }
        }
    }

    /**
     * 设置横向滚动
     * @param newScrollLeft Int
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollLeft(newScrollLeft: Int, scrollType: Int?): Boolean {
        return if (scrollType == null) {
            invoke(JSCODE.SET_SCROLL_LEFT, newScrollLeft)
        } else {
            invoke(JSCODE.SET_SCROLL_LEFT, newScrollLeft, scrollType)
        } == true
    }

    /**
     * 设置纵向滚动
     * @param newScrollTop Int
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollTop(newScrollTop: Int, scrollType: Int?): Boolean {
        return if (scrollType == null) {
            invoke(JSCODE.SET_SCROLL_TOP, newScrollTop)
        } else {
            invoke(JSCODE.SET_SCROLL_TOP, newScrollTop, scrollType)
        } == true
    }

    /**
     * 设置滚动位置
     * @param position INewScrollPosition
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollPosition(position: INewScrollPosition, scrollType: Int?): Boolean {
        return execute(JSCODE.SET_SCROLL_POSITION.format(JSON.toJSONString(position), "$scrollType")) == true

    }

    /**
     * 设置滚动位置
     * @param positionLeft Int
     * @param positionTop Int
     * @param scrollType Int?
     * @return Boolean
     */
    fun setScrollPositionXY(positionLeft: Int, positionTop: Int, scrollType: Int?): Boolean {
        return if (scrollType == null) {
            invoke(JSCODE.SET_SCROLL_POSITION_XY, positionLeft, positionTop)
        } else {
            invoke(JSCODE.SET_SCROLL_POSITION_XY, positionLeft, positionTop, scrollType)
        } == true
    }


    /**
     *
     * @return Int?
     */
    fun getScrollWidth(): Int? {
        return when(val r = invoke(JSCODE.GET_SCROLL_WIDTH)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getScrollLeft(): Int? {
        return when(val r = invoke(JSCODE.GET_SCROLL_LEFT)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getScrollHeight(): Int?{
        return when(val r = invoke(JSCODE.GET_SCROLL_HEIGHT)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @return Int?
     */
    fun getScrollTop(): Int? {
        return when(val r = invoke(JSCODE.GET_SCROLL_TOP)) {
            is Int -> r
            else -> null
        }
    }

    /**
     *
     * @param selections Array<ISelection>
     * @return Boolean
     */
    fun setSelections(selections: Array<SelectionObject>): Boolean {
        return execute(JSCODE.SET_SELECTIONS.format(JSON.toJSONString(selections))) == true
    }

    /**
     *
     * @param selection RangeObject
     * @return Boolean
     */
    fun setSelection(selection: RangeObject): Boolean {
        return execute(JSCODE.SET_SELECTION.format(JSON.toJSONString(selection))) == true
    }

    /**
     *
     * @param selection SelectionObject
     * @return Boolean
     */
    fun setSelection(selection: SelectionObject): Boolean {
        return execute(JSCODE.SET_SELECTION.format(JSON.toJSONString(selection))) == true
    }

    /**
     *
     * @param column Int
     * @param lineNumber Int
     * @return Boolean
     */
    fun setPosition(column: Int, lineNumber: Int): Boolean {
        return execute(JSCODE.SET_POSITION.format("""{column:$column, lineNumber:$lineNumber}""")) == true
    }


    /**
     *
     * @param position IPosition
     * @return Boolean
     */
    fun setPosition(position: IPosition): Boolean {
        return execute(JSCODE.SET_POSITION.format(JSON.toJSONString(position))) == true
    }

    /**
     *
     * @param value String
     * @return Boolean
     */
    fun setValue(value: String): Boolean {
        return invoke(JSCODE.SET_VALUE, value) == true
    }



    /**
     *
     * @param source String?
     * @param edits Array<IIdentifiedSingleEditOperation>
     * @param endCursorState Array<SelectionObject>?
     * @return Boolean
     */
    fun executeEdits(source: String?, edits: Array<IIdentifiedSingleEditOperation>, endCursorState: Array<SelectionObject>?): Boolean {
        return if (endCursorState == null) {
            invoke(JSCODE.EXECUTE_EDITS.format(source, JSON.toJSONString(edits), "undefined"))
        } else {
            invoke(JSCODE.EXECUTE_EDITS.format(source, JSON.toJSONString(edits), JSON.toJSONString(endCursorState)))
        } == true
    }

    /**
     * 直接触发一个动作
     * @param source String
     * @param handlerId String
     * @param payload Any?
     */
    fun trigger(source: String?, handlerId: String, payload: Any?): Boolean {
        return (if (payload == null)
            invoke(JSCODE.TRIGGER, source, handlerId)
        else
            invoke(JSCODE.TRIGGER, source, handlerId, payload)) == true
    }


    ///////////////////////////////工具、辅助方法/////////////////////////////////
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

    inline fun <reified T> ensureString(
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

    inline fun <reified T> ensureJSONObject(
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

    inline fun <reified T> ensureJSONObject(
        input: Any,
        error: Pair<String, Throwable> = (JS_INVOKE_EXCEPTION to ERROR_UNEXPECTED_RETURN_VALUE),
        block: (JSONObject) -> T?
    ): T? {
        return ensureString(input) { inputString ->
            ensureJSONObject(inputString, error, block)
        }
    }

    inline fun <reified T> ensureJSONArray(
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

    inline fun <reified T> ensureTypeT(
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

    ///////////////////////////////事件监听////////////////////////////////////////////////
    override fun listen(eventId: Int, callback: (eventId: Int, e: Any?) -> Any?) {
        if (eventId in editorEvents)
            editorEvents.remove(eventId)
        editorEvents[eventId] = callback
    }

    override fun unlisten(eventId: Int) {
        if (eventId in editorEvents)
            editorEvents.remove(eventId)
    }

    override fun isListened(eventId: Int): Boolean {
        return eventId in editorEvents.keys
    }

    fun setOnMouseDownListener(listener: ((eventId: Int, event: EditorMouseEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onMouseDown)
            return
        }

        listen(EditorEvents.onMouseDown) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onMouseUp(listener: ((eventId: Int, event: EditorMouseEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onMouseUp)
            return
        }

        listen(EditorEvents.onMouseUp) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onMouseMove(listener: ((eventId: Int, event: EditorMouseEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onMouseMove)
            return
        }
        listen(EditorEvents.onMouseMove) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onMouseLeave(listener: ((eventId: Int, event: EditorMouseEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onMouseLeave)
            return
        }
        listen(EditorEvents.onMouseLeave) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, EditorMouseEvent(e))
            }
        }
    }

    fun onKeyUp(listener: ((eventId: Int, event: KeyBoardEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onKeyUp)
            return
        }
        listen(EditorEvents.onKeyUp) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, KeyBoardEvent(e))
            }
        }
    }

    fun onKeyDown(listener: ((eventId: Int, event: KeyBoardEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onKeyDown)
            return
        }
        listen(EditorEvents.onKeyDown) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, KeyBoardEvent(e))
            }
        }
    }

    fun onDidScrollChange(listener: ((eventId: Int, event: ScrollEvent)->Unit)?) {
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

    fun onDidPaste(listener: ((eventId: Int, event: PasteEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidPaste)
            return
        }
        listen(EditorEvents.onDidPaste) { eventId: Int, e: Any? ->
            if (e is JSObject) {
                listener(eventId, PasteEvent(e))
            }
        }
    }

    fun onDidLayoutChange(listener: ((eventId: Int, event: EditorLayoutInfo)->Unit)?) {
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

    fun onGainFocus(listener: ((eventId: Int)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidFocusEditorWidget)
            unlisten(EditorEvents.onDidFocusEditorText)
            return
        }
        val callback = { eventId: Int, _: Any? ->
            listener(eventId)
        }
        listen(EditorEvents.onDidFocusEditorText, callback)
        listen(EditorEvents.onDidFocusEditorWidget, callback)
    }

    fun onLostFocus(listener: ((eventId: Int)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidBlurEditorText)
            unlisten(EditorEvents.onDidBlurEditorWidget)
            return
        }
        val callback = { eventId: Int, _: Any? ->
            listener(eventId)
        }
        listen(EditorEvents.onDidBlurEditorText, callback)
        listen(EditorEvents.onDidBlurEditorWidget, callback)
    }

    fun onContextMenu(listener: ((eventId: Int, event: EditorMouseEvent)->Unit)?) {
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

    fun onDidChangeConfiguration(listener:( (eventId: Int, event: ConfigurationChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeConfiguration)
            return
        }
        listen(EditorEvents.onDidChangeConfiguration) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ConfigurationChangedEvent(e))
        }
    }

    fun onDidChangeCursorPosition(listener:( (eventId: Int, event: CursorPositionChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeCursorPosition)
            return
        }
        listen(EditorEvents.onDidChangeCursorPosition) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, CursorPositionChangedEvent(e))
        }
    }

    fun onDidChangeCursorSelection(listener:( (eventId: Int, event: CursorSelectionChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeCursorSelection)
            return
        }
        listen(EditorEvents.onDidChangeCursorSelection) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, CursorSelectionChangedEvent(e))
        }
    }

    fun onDidChangeModelContent(listener:( (eventId: Int, event: ModelContentChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeModelContent)
            return
        }
        listen(EditorEvents.onDidChangeModelContent) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ModelContentChangedEvent(e))
        }
    }

    fun onDidChangeModelLanguage(listener:( (eventId: Int, event: ModelLanguageChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeModelLanguage)
            return
        }
        listen(EditorEvents.onDidChangeModelLanguage) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ModelLanguageChangedEvent(e))
        }
    }

    fun onDidChangeModelOptions(listener:( (eventId: Int, event: ModelOptionsChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidChangeModelOptions)
            return
        }
        listen(EditorEvents.onDidChangeModelOptions) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ModelOptionsChangedEvent(e))
        }
    }

    fun onDidContentSizeChange(listener:( (eventId: Int, event: ContentSizeChangedEvent)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidContentSizeChange)
            return
        }
        listen(EditorEvents.onDidContentSizeChange) { eventId: Int, e: Any? ->
            if (e is JSObject)
                listener(eventId, ContentSizeChangedEvent(e))
        }
    }
    fun onDidDispose(listener: ((eventId: Int)->Unit)?) {
        if (listener == null) {
            unlisten(EditorEvents.onDidDispose)
            return
        }
        listen(EditorEvents.onDidDispose) { eventId: Int, _: Any? ->
                listener(eventId)
        }
    }
}

//fun main() {
//    val selections = arrayOf(
//        SelectionObject(1, 2,3,4),
//        SelectionObject(2, 2,3,4),
//        SelectionObject(3, 2,3,4),
//    )
//    val a = JSON.toJSONString(selections)
//    println(a)
//    println(MonacoEditor.JSCODE.SET_SELECTIONS.format(a))
//}