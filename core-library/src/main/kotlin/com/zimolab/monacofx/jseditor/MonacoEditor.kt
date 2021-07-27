package com.zimolab.monacofx.jseditor


import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zimolab.monacofx.jsbase.JsBridge
import com.zimolab.monacofx.jsbase.execute
import com.zimolab.monacofx.jsbase.invoke
import com.zimolab.monacofx.jseditor.Globals.JS_EDITOR_NAMESPACE
import com.zimolab.monacofx.jseditor.Globals.JS_EXCEPTION
import com.zimolab.monacofx.jseditor.event.IEditorEventListener
import com.zimolab.monacofx.jseditor.language.ILanguageExtensionPoint
import com.zimolab.monacofx.jseditor.options.IComputedEditorOptions
import com.zimolab.monacofx.jseditor.options.IStandaloneEditorConstructionOptions
import com.zimolab.monacofx.MonacoEditorFx
import javafx.scene.web.WebEngine
import netscape.javascript.JSObject

class MonacoEditor(val webEngine: WebEngine, val monacoFx: MonacoEditorFx) : JsBridge, IEditorEventListener {
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
    }

    var jsEditor: JSObject? = null
    val addedActions: MutableMap<String, IActionDescriptor> by lazy { mutableMapOf() }
    val addedCommands: MutableMap<String, ICommandHandler> by lazy { mutableMapOf() }
    private val editorEvents: MutableMap<Int, (args: Any?)->Any?> by lazy {
        mutableMapOf()
    }


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

    fun onEditorEvent(event: Int, e: Any?) {
        if (event in editorEvents)
            editorEvents[event]?.invoke(e)
    }
    /***********************************************************************/


    fun isReady(): Boolean {
        return invoke(JSCODE.IS_READY) == true
    }

    /**
     * 创建Editor实例
     * @param createOptions IStandaloneEditorConstructionOptions?
     * @return Boolean
     */
    fun create(createOptions: IStandaloneEditorConstructionOptions?): Boolean {
        return execute(JSCODE.CREATE.format(JSON.toJSONString(createOptions))) == true
    }

    /**
     * 自动重新布局
     */
    fun autoLayout(): Boolean {
        return invoke(JSCODE.AUTO_LAYOUT) == true
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

    override fun listen(event: Int, callback: (args: Any?) -> Any?) {
        if (event in editorEvents)
            editorEvents.remove(event)
        editorEvents[event] = callback
    }

    override fun unlisten(event: Int) {
        if (event in editorEvents)
            editorEvents.remove(event)
    }

    override fun isListened(event: Int): Boolean {
        return event in editorEvents
    }
}