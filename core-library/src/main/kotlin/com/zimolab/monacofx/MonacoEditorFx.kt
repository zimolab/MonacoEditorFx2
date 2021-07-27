package com.zimolab.monacofx

import com.zimolab.monacofx.jseditor.Globals.JS_CHECK_INTERVAL
import com.zimolab.monacofx.jseditor.Globals.DEFAULT_LOAD_TIMEOUT
import com.zimolab.monacofx.jseditor.Globals.DEFAULT_MONACO_EDITOR_INDEX
import com.zimolab.monacofx.jseditor.Globals.ILLEGAL_JS_ID_EXCEPTION
import com.zimolab.monacofx.jseditor.Globals.JS_EXCEPTION
import com.zimolab.monacofx.jseditor.Globals.JS_GLOBAL_OBJECT_ID
import com.zimolab.monacofx.jseditor.Globals.JS_HOST_ENV_READY_EVENT
import com.zimolab.monacofx.jseditor.MonacoEditor
import com.zimolab.monacofx.jseditor.MonacoEditor.Companion.JS_EDITOR_ID
import com.zimolab.monacofx.jseditor.Globals.JS_UNDEFINED
import com.zimolab.monacofx.jseditor.options.IStandaloneEditorConstructionOptions
import com.zimolab.monacofx.util.Logger
import com.zimolab.monacofx.jsbase.*
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
    customMonacoEditorImpl: InputStream? = null
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

    // 定义内置的webview
    private val webView: WebView by lazy {
        WebView()
    }

    // webview的engine
    private val webEngine: WebEngine by lazy {
        webView.engine
    }

    // 当前页面加载作业
    private val currentLoadWorker: Worker<Void> by lazy {
        webEngine.loadWorker
    }

    val editor: MonacoEditor = MonacoEditor(webEngine, this)


    // 承载Monaco editor的html，默认使用自带的实现
    // 也可以通过setMonacoEditorIndex().reload()来载入自定义的实现
    private val defaultMonacoEditorImpl = DEFAULT_MONACO_EDITOR_INDEX
    private var customMonacoEditorImpl: InputStream? = null
    private var useDefaultMonacoEditorImpl = true
    private var jsHostEnvObjects = arrayListOf(Logger, editor)


    init {
        // 决定是否使用自定义的Monaco Editor实现
        if (customMonacoEditorImpl != null) {
            useCustomMonacoEditorImpl(customMonacoEditorImpl)
        } else {
            useDefaultMonacoEditorImpl()
        }

        // 将webView添加到子节点序列中
        children.add(webView)

        // WebEngine相关配置
        webEngine.javaScriptEnabledProperty().set(true)
        setupInternalErrorHandler()
        setLoadStateHandler()
        // 首次加载
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
        // 如果正在加载，则直接返回，等待当前页面加载完成
        if (currentLoadWorker.isRunning || preparingMonacoEditor.get()) {
            return
        }

        if (useDefaultMonacoEditorImpl) {
            // 使用默认的MonacoEditor实现
            val indexUrl = javaClass.getResource(defaultMonacoEditorImpl)
            assert(indexUrl != null)
            webEngine.load(indexUrl!!.toExternalForm())
        } else {
            // 使用自定义的实现（从流读入html文件）
            assert(customMonacoEditorImpl != null)
            val reader = BufferedReader(InputStreamReader(customMonacoEditorImpl!!))
            val htmlContent = reader.readText()
            reader.close()
            webEngine.loadContent(htmlContent, "text/html")
        }
    }

    private fun onPageLoad() {
        // 页面加载完成之后，需要检查MonacoEditor是否已经准备就绪
        // 这需要通过调用页面中的js代码实现
        preparingMonacoEditor.set(true)
        // 先拿到js中的全局对象，在浏览器环境中这个对象是window
        val globalObject = webEngine.execute(JS_GLOBAL_OBJECT_ID)
        if (globalObject is JSException) {
            afterLoad(false, JS_EXCEPTION to globalObject)
            return
        }
        if (globalObject !is JSObject) {
            afterLoad(false)
            return
        }

        // 注入js宿主环境对象
        if (!injectJavaObjects(globalObject, jsHostEnvObjects)) {
            afterLoad(false)
            return
        }
        // 接着发送HOST_ENV_READY_EVENT事件，表明宿主环境已经准备就绪，可以在js调用宿主提供的对象了
        val err = globalObject.invoke(JS_HOST_ENV_READY_EVENT)
        if (err is JSException) {
            afterLoad(false, JS_EXCEPTION to err)
            return
        }

        // 为了不阻塞主线程，切换到工作线程
        launch(Dispatchers.Default) {
            val startTime = System.currentTimeMillis()
            val ready = AtomicBoolean(false)
            var jsEditor: JSObject? = null
            // js可能没有准备好，所以在给定的超时时间之内我们隔一段时间（100ms）查询一次
            while (!ready.get() && (System.currentTimeMillis() - startTime < loadTimeout)) { // 继续循环的条件：js未准备好 且（&&）未超时
                // 所有js调用操作必须在主线程进行，所以切换回主线程
                launch(Dispatchers.Main) checkJs@{
                    val obj = webEngine.execute(JS_EDITOR_ID)

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

            // 切回主线程
            launch(Dispatchers.Main) {
                if (ready.get()) {
                    if(editor.create(createOptions)) {
                        editor.jsEditor = jsEditor
                        JsVariableReference.init(webEngine)
                        afterLoad(true)
                    } else {
                        editor.jsEditor = null
                        afterLoad(false)
                    }
                } else {
                    editor.jsEditor = null
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
        // 将webview布局到容器中，并将其占满整个容器
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
}