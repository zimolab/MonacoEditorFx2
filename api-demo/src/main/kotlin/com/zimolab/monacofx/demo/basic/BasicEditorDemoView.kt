package com.zimolab.monacofx.demo.basic

import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.monaco.editor.event.keyboard.KeyBoardEvent
import com.zimolab.monacofx.monaco.editor.event.miscellaneous.PasteEvent
import com.zimolab.monacofx.monaco.editor.event.mouse.EditorMouseEvent
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import java.time.LocalDateTime.now
import java.util.*
class BasicEditorDemoView : View("MonacoEditorFx基本功能演示") {
    override val root: VBox by fxml("/view/BasicEditorDemoView.fxml")
    private val leftStatusLabel by fxid<Label>()
    private val centerStatusLabel by fxid<Label>()
    private val rightStatusLabel by fxid<Label>()
    private val editorPanel by fxid<VBox>()
    private val outputTextArea by fxid<TextArea>()
    private val monacoEditorFx: MonacoEditorFx by lazy {
        MonacoEditorFx()
    }

    init {
        info("编辑器初始化")
        initWindow()
        initMonacoEditorFx()
    }

    private fun initWindow() {
        updateLeftStatus("")
        updateCenterStatus("")
        updateRightStatus("")
        outputTextArea.editableProperty().set(false)
        outputTextArea.contextMenu = OutputTextAreaContextMenu(outputTextArea)
        interceptExitEvent()
    }

    private fun initMonacoEditorFx() {
        updateLeftStatus("初始化")
        // 监听编辑器加载状态变化
        monacoEditorFx.loadStateProperty.addListener { _, oldState, newState ->
            updateLeftStatus(newState.name)
            info("编辑加载状态发生变化：（$oldState -> $newState）")
            if (newState == MonacoEditorFx.LoadState.LOADED) {
                onEditorLoad()
            }
        }
        // 监听编辑器就绪状态变化
        monacoEditorFx.editorReadyProperty.addListener { _, _, ready ->
            if (ready) {
                onEditorReady()
            }
        }
        // 监听编辑器内部错误
        monacoEditorFx.internalErrorProperty.addListener { _, _, e ->
            error("发生了一个内部错误")
            if (e != null)
                error("${e.first}: ${e.second.stackTrace}")
        }
        monacoEditorFx.vgrow = Priority.ALWAYS
        editorPanel.children.add(monacoEditorFx)

        // 手动调用reload()方法来加载编辑器
        monacoEditorFx.reload()
    }

    fun onEditorCheckBoxAction(event: ActionEvent) {
        editorReady {
            val source = event.source as CheckBox
            when(source.id) {
                "onMouseDown" ->
                    if (source.isSelected) {
                        val r = monacoEditorFx.editor.onMouseDown(this::onEditorMouseEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onMouseDown(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onMouseUp" ->
                    if (source.isSelected) {
                        val r = monacoEditorFx.editor.onMouseUp(this::onEditorMouseEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onMouseUp(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onMouseMove" ->
                    if (source.isSelected) {
                        val r = monacoEditorFx.editor.onMouseMove(this::onEditorMouseEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onMouseMove(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onMouseLeave" ->
                    if (source.isSelected) {
                        val r = monacoEditorFx.editor.onMouseLeave(this::onEditorMouseEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onMouseLeave(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onKeyUp" ->
                    if (source.isSelected) {
                        val r = monacoEditorFx.editor.onKeyUp(this::onEditorKeyEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onKeyUp(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onKeyDown" ->
                    if (source.isSelected) {
                        val r = monacoEditorFx.editor.onKeyDown(this::onEditorKeyEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onKeyDown(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onDidPaste" ->
                    if(source.isSelected) {
                        val r = monacoEditorFx.editor.onDidPaste(this::onEditorPasteEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onDidPaste(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onGainFocus" ->
                    if(source.isSelected) {
                        val r = monacoEditorFx.editor.onDidPaste(this::onEditorPasteEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onDidPaste(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                "onLostFocus" ->
                    if(source.isSelected) {
                        val r = monacoEditorFx.editor.onDidPaste(this::onEditorPasteEvent)
                        info("监听事件: ${source.id}（调用结果：$r）")
                    } else {
                        val r = monacoEditorFx.editor.onDidPaste(null)
                        info("取消事件监听: ${source.id}（调用结果：$r）")
                    }
                else ->
                    warn("未知ActionEvent：$source")
            }
        }
    }

    fun onEditorMouseEvent(eventId: Int, event: EditorMouseEvent) {
        debug("触发编辑器鼠标事件：\n" +
                "\teventId=$eventId\n" +
                "\tposx, posy=(${event.event.posx}, ${event.event.posy})\n" +
                "\tleftButton=${event.event.leftButton}, rightButton=${event.event.rightButton}, middleButton=${event.event.middleButton}\n" +
                "\tbuttons=${event.event.buttons}\n" +
                "\taltKey=${event.event.altKey}, ctrlKey=${event.event.ctrlKey}, shiftKey=${event.event.shiftKey}\n" +
                "\ttargetPosition=${event.target.position}\n" +
                "\ttargetRange=${event.target.range}")

    }

    fun onEditorKeyEvent(eventId: Int, event: KeyBoardEvent) {
        debug("触发编辑器键盘事件：\n" +
                "\teventId=$eventId\n" +
                "\tkeycode=${event.keyCode}, altKey=${event.altKey}, ctrlKey=${event.ctrlKey}, shiftKey=${event.shiftKey}\n" +
                "\tcode=${event.code}\n" +
                "\tkey=${event.browserEvent.type}")
    }

    fun onEditorPasteEvent(eventId: Int, event: PasteEvent) {
        debug("触发编辑器粘贴事件：\n" +
                "\teventId=$eventId\n" +
                "\tmode=${event.mode}\n" +
                "\trange=${event.range}")
    }

    fun onEditorGainFocus(eventId: Int) {

    }

    private fun onEditorLoad() {
        info("编辑器加载成功")
    }

    private fun onEditorReady() {
        info("编辑器已就绪")
        updateLeftStatus("Ready")
    }

    private fun interceptExitEvent() {
        // 拦截窗口关闭事件
        currentStage?.setOnCloseRequest { event->
            // 创建对话框，询问用户是否要退出
            confirmation("", "程序即将退出，未保存数据将会丢失！") { buttonType ->
                if (buttonType != ButtonType.OK)
                    event.consume()
                else {
                    Platform.exit()
                }
            }
        }
    }

    private fun updateLeftStatus(status: String) {
        leftStatusLabel.text = status
    }

    private fun updateCenterStatus(status: String) {
        centerStatusLabel.text = status
    }

    private fun updateRightStatus(status: String) {
        rightStatusLabel.text = status
    }

    private fun output(tag: String, message: String) {
        outputTextArea.appendText("[${now()}]${tag.uppercase(Locale.getDefault())}: $message\n")
    }

    private fun log(message: String) = output("log", message)
    private fun info(message: String) = output("info", message)
    private fun error(message: String) = output("error", message)
    private fun warn(message: String) = output("warn", message)
    private fun debug(message: String) = output("debug", message)

    private fun clearLog() {
        outputTextArea.text = ""
    }

    private fun editorReady(block: ()->Unit) {
        if (monacoEditorFx.editorReadyProperty.value) {
            block()
        } else {
            warn("编辑器尚未准备就绪")
        }
    }
}

class OutputTextAreaContextMenu(private val parent: TextArea): ContextMenu() {
    private val selectAll = MenuItem("全选")
    private val copy =  MenuItem("复制")
    private val separator = SeparatorMenuItem()
    private val clear = MenuItem("清除")

    init {
        if (parent.selectedTextProperty().isEmpty.value)
            copy.disableProperty().set(true)

        selectAll.setOnAction {
            onAction(it)
        }
        copy.setOnAction {
            onAction(it)
        }
        clear.setOnAction {
            onAction(it)
        }

        parent.selectedTextProperty().addListener { _, _, selected ->
            if (selected != null && selected.isNotEmpty())
                copy.disableProperty().set(false)
            else
                copy.disableProperty().set(true)
        }

        items.addAll(
            selectAll,
            copy,
            separator,
            clear)
    }

    private fun onAction(event: ActionEvent) {
        when(event.source) {
            selectAll -> parent.selectAll()
            copy -> parent.copy()
            else -> parent.clear()
        }
    }
}