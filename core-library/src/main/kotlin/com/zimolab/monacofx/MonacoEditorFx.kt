package com.zimolab.monacofx

import com.zimolab.monacofx.monaco.Globals.JS_CHECK_INTERVAL
import com.zimolab.monacofx.monaco.Globals.DEFAULT_LOAD_TIMEOUT
import com.zimolab.monacofx.monaco.Globals.DEFAULT_MONACO_EDITOR_INDEX
import com.zimolab.monacofx.monaco.Globals.ILLEGAL_JS_ID_EXCEPTION
import com.zimolab.monacofx.monaco.Globals.JS_EXCEPTION
import com.zimolab.monacofx.monaco.Globals.JS_GLOBAL_OBJECT
import com.zimolab.monacofx.monaco.Globals.JS_HOST_ENV_READY_EVENT
import com.zimolab.monacofx.monaco.editor.MonacoEditor
import com.zimolab.monacofx.monaco.editor.MonacoEditor.Companion.JS_EDITOR
import com.zimolab.monacofx.monaco.Globals.JS_UNDEFINED
import com.zimolab.monacofx.monaco.editor.options.IStandaloneEditorConstructionOptions
import com.zimolab.monacofx.util.Logger
import com.zimolab.monacofx.jsbase.*
import com.zimolab.monacofx.util.Clipboard
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.concurrent.Worker
import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.layout.Region
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import kotlinx.coroutines.*
import netscape.javascript.JSException
import netscape.javascript.JSObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicBoolean


class MonacoEditorFx(
    private val createOptions: IStandaloneEditorConstructionOptions? = null,
    customMonacoEditorImpl: InputStream? = null,
    autoLoad: Boolean = false
) : Region(), CoroutineScope by MainScope() {

    enum class LoadState(val value: Int) {
        LOAD_INIT(0),
        LOADING(1),
        LOADED(2),
        LOAD_FAILED(3),
        LOAD_CANCELLED(4)
    }


    private val loadStatePropertyWrapper: ReadOnlyObjectWrapper<LoadState> =
        ReadOnlyObjectWrapper(this, "reloadState", LoadState.LOAD_INIT)
    val loadStateProperty: ReadOnlyObjectProperty<LoadState> = loadStatePropertyWrapper.readOnlyProperty

    private val internalErrorPropertyWrapper: ReadOnlyObjectWrapper<Pair<String, Throwable>?> =
        ReadOnlyObjectWrapper(this, "internalError", null)
    val internalErrorProperty: ReadOnlyObjectProperty<Pair<String, Throwable>?> =
        internalErrorPropertyWrapper.readOnlyProperty

    private val editorReadyPropertyWrapper: ReadOnlyBooleanWrapper = ReadOnlyBooleanWrapper(this, "editorReady", false)
    val editorReadyProperty: ReadOnlyBooleanProperty = editorReadyPropertyWrapper.readOnlyProperty

    private val preparingMonacoEditor = AtomicBoolean(false)

    var loadTimeout: Long = DEFAULT_LOAD_TIMEOUT
        set(value) {
            field = if (value <= 100) DEFAULT_LOAD_TIMEOUT else value
        }

    // ???????????????webview
    private val webView: WebView by lazy {
        WebView()
    }

    // webview???engine
    private val webEngine: WebEngine by lazy {
        webView.engine
    }

    // ????????????????????????
    private val currentLoadWorker: Worker<Void> by lazy {
        webEngine.loadWorker
    }

    val editor: MonacoEditor = MonacoEditor(webEngine, this)


    // ??????Monaco editor???html??????????????????????????????
    // ???????????????setMonacoEditorIndex().reload()???????????????????????????
    private val defaultMonacoEditorImpl = DEFAULT_MONACO_EDITOR_INDEX
    private var customMonacoEditorImpl: InputStream? = null
    private var useDefaultMonacoEditorImpl = true
    private var jsHostEnvObjects = arrayListOf(Logger, editor, Clipboard)


    init {
        // ??????????????????????????????Monaco Editor??????
        if (customMonacoEditorImpl != null) {
            useCustomMonacoEditorImpl(customMonacoEditorImpl)
        } else {
            useDefaultMonacoEditorImpl()
        }

        // ???webView???????????????????????????
        children.add(webView)

        // WebEngine????????????
        webEngine.javaScriptEnabledProperty().set(true)
        webView.contextMenuEnabledProperty().set(false)
        setupInternalErrorHandler()
        setLoadStateHandler()
        if (autoLoad)
            reload()
    }

    private fun setLoadStateHandler() {
        currentLoadWorker.stateProperty().addListener { _, _, currentState ->
            when (currentState) {
                Worker.State.READY,
                Worker.State.SCHEDULED,
                Worker.State.RUNNING -> {
                    this.loadStatePropertyWrapper.set(LoadState.LOADING)
                    this.editorReadyPropertyWrapper.set(false)
                }

                Worker.State.SUCCEEDED -> {
                    this.editorReadyPropertyWrapper.set(false)
                    onPageLoad()
                }

                Worker.State.FAILED -> {
                    this.loadStatePropertyWrapper.set(LoadState.LOAD_FAILED)
                    this.editorReadyPropertyWrapper.set(false)
                }
                else -> {
                    this.loadStatePropertyWrapper.set(LoadState.LOAD_CANCELLED)
                    this.editorReadyPropertyWrapper.set(false)
                }
            }
        }
//        webEngine.documentProperty().addListener { observable, oldDoc, newDoc ->
//            if (newDoc == null) {
//                this.loadStatePropertyWrapper.set(LoadState.LOAD_FAILED)
//                this.editorReadyPropertyWrapper.set(false)
//            } else {
//                onPageLoad()
//            }
//        }
    }

    private fun setupInternalErrorHandler() {
        webEngine.setOnError { event ->
            event.consume()
            internalErrorOccurs(event.message, event.exception)
        }
    }

    fun useCustomMonacoEditorImpl(monacoEditorImpl: InputStream) {
        customMonacoEditorImpl = monacoEditorImpl
        useDefaultMonacoEditorImpl = false
    }

    fun useDefaultMonacoEditorImpl() {
        customMonacoEditorImpl = null
        useDefaultMonacoEditorImpl = true
    }

    fun reload() {
        // ?????????????????????????????????????????????????????????????????????
        if (currentLoadWorker.isRunning || preparingMonacoEditor.get()) {
            return
        }
        dispose()

        if (useDefaultMonacoEditorImpl) {
            // ???????????????MonacoEditor??????
            val indexUrl = javaClass.getResource(defaultMonacoEditorImpl)
            assert(indexUrl != null)
            webEngine.load(indexUrl!!.toExternalForm())
        } else {
            // ???????????????????????????????????????html?????????
            assert(customMonacoEditorImpl != null)
            val reader = BufferedReader(InputStreamReader(customMonacoEditorImpl!!))
            val htmlContent = reader.readText()
            reader.close()
            webEngine.loadContent(htmlContent, "text/html")
        }
    }

    private fun onPageLoad() {
        // ???????????????????????????????????????MonacoEditor????????????????????????
        // ?????????????????????????????????js????????????
        preparingMonacoEditor.set(true)
        // ?????????js?????????????????????????????????????????????????????????window
        val globalObject = webEngine.execute(JS_GLOBAL_OBJECT)
        if (globalObject is JSException) {
            afterLoad(false, JS_EXCEPTION to globalObject)
            return
        }
        if (globalObject !is JSObject) {
            afterLoad(false)
            return
        }

        // ??????js??????????????????
        if (!injectJavaObjects(globalObject, jsHostEnvObjects)) {
            afterLoad(false)
            return
        }

        // ????????????HOST_ENV_READY_EVENT?????????????????????????????????????????????????????????js??????????????????????????????
        val err = globalObject.invoke(JS_HOST_ENV_READY_EVENT)
        if (err is JSException) {
            afterLoad(false, JS_EXCEPTION to err)
            return
        }

        // ????????????????????????????????????????????????
        launch(Dispatchers.Default) {
            val startTime = System.currentTimeMillis()
            val ready = AtomicBoolean(false)
            var jsEditor: JSObject? = null
            // js????????????????????????????????????????????????????????????????????????????????????100ms???????????????
            while (!ready.get() && (System.currentTimeMillis() - startTime < loadTimeout)) { // ????????????????????????js???????????? ??????&&????????????
                // ??????js???????????????????????????????????????????????????????????????
                launch(Dispatchers.Main) checkJs@{
                    val obj = webEngine.execute(JS_EDITOR)

                    if (obj is String && obj == JS_UNDEFINED) {
                        return@checkJs
                    }

                    if (obj is JSException) {
                        internalErrorOccurs(JS_EXCEPTION, obj)
                        return@checkJs
                    }

                    if (obj is JSObject) {
                        jsEditor = obj
                        ready.set(true)
                        return@checkJs
                    }
                }.join()
                delay(JS_CHECK_INTERVAL)
            }

            // ???????????????
            launch(Dispatchers.Main) {
                if (ready.get()) {
                    if(editor.create(createOptions)) {
                        editor.onJsEditorReady(jsEditor)
                        JsVariableReference.init(webEngine)
                        webView.contextMenuEnabledProperty().set(true)
                        afterLoad(true)
                    } else {
                        editor.onJsEditorReady(null)
                        afterLoad(false)
                    }
                } else {
                    editor.onJsEditorReady(null)
                    afterLoad(false)
                }
            }
        }
    }

    private fun afterLoad(success: Boolean, exception: Pair<String, Throwable>? = null) {
        if (success) {
            loadStatePropertyWrapper.set(LoadState.LOADED)
            editorReadyPropertyWrapper.set(true)
            preparingMonacoEditor.set(false)
        } else {
            loadStatePropertyWrapper.set(LoadState.LOAD_FAILED)
            editorReadyPropertyWrapper.set(false)
            preparingMonacoEditor.set(false)
            if (exception != null) internalErrorOccurs(exception.first, exception.second)
        }
    }

    private fun injectJavaObjects(globalObject: JSObject, objects: Collection<JsBridge>): Boolean {
        objects.forEach { obj ->
            try {
                globalObject.inject(obj)
            } catch (e: IllegalArgumentException) {
                internalErrorOccurs(ILLEGAL_JS_ID_EXCEPTION, e)
                return false
            } catch (e: JSException) {
                internalErrorOccurs(JS_EXCEPTION, e)
                return false
            }
        }
        return true
    }

    fun internalErrorOccurs(description: String, error: Throwable) {
        internalErrorPropertyWrapper.set(description to error)
    }

    override fun layoutChildren() {
        super.layoutChildren()
        // ???webview????????????????????????????????????????????????
        val positionX = 0.0
        val positionY = 0.0
        val positionWidth = width
        val positionHeight = height
        val positionHorizontal = HPos.CENTER
        val positionVertical = VPos.CENTER
        val positionBaselineOffset = 0.0

        layoutInArea(
            webView,
            positionX,
            positionY,
            positionWidth,
            positionHeight,
            positionBaselineOffset,
            positionHorizontal,
            positionVertical
        )
    }

    fun dispose() {
        if(editor.dispose()) {
            editorReadyPropertyWrapper.set(false)
            editor.onJsEditorReady(null)
        }
    }
}