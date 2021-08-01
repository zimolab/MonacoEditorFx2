package com.zimolab.monacofx.demo

import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.monaco.editor.IActionDescriptor
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*
import java.util.*

class DemoView : View("MonacoEditorFx Demo") {
    override val root: VBox by fxml("/view/MonacoEditorFxDemoView.fxml")
    private val buttonReload: Button by fxid()
    private val labelReady: Label by fxid()
    private val labelLoadState: Label by fxid()
    private val labelError: Label by fxid()
    private var currentId: String = UUID.randomUUID().toString()
    private var actionTest: IActionDescriptor? = null
    private val monacoEditorFx: MonacoEditorFx by lazy {
        MonacoEditorFx()
    }

    init {
        monacoEditorFx.loadStateProperty.addListener { observable, oldValue, newValue ->
            labelLoadState.text = "loadState: ${newValue}"
        }
        monacoEditorFx.editorReadyProperty.addListener { observable, oldValue, newValue ->
            labelReady.text = "ready: ${newValue}"
        }
        monacoEditorFx.internalErrorProperty.addListener { observable, oldValue, newValue ->
            labelError.text = "InternalError: ${newValue?.first}"
        }

//        monacoEditorFx.editor.setOnKeyUpListener { eventId, event ->
//            println("${event.keyCode}, ${event.code}, ${event.ctrlKey}")
//        }
//        monacoEditorFx.editor.setOnScrollEvent { eventId, event ->
//            println("${event.scrollLeft}, ${event.scrollTop}")
//        }
        monacoEditorFx.editor.onDidPaste { eventId, event ->
            println("${event.mode}, ${event.range}")
        }
//        monacoEditorFx.editor.setOnChangeCursorPositionListener { eventId, e ->
//            println("change cursor position: (${e.position.lineNumber}, ${e.position.column}) reason: ${e.reason} ${e.secondaryPositions}")
//        }
//        monacoEditorFx.editor.setOnChangeCursorSelectionListener { eventId, event ->
//            println("ChangeCursorSelection: ${event.selection}")
//        }
//        monacoEditorFx.editor.setOnLostFocusListener {
//            println("lost focus")
//        }

        root.children.add(monacoEditorFx)
    }

    fun onButtonReloadAction() {
        monacoEditorFx.reload()
    }

    fun onButtonTestAction() {
        if (monacoEditorFx.editorReadyProperty.value) {
            val langs = monacoEditorFx.editor.getLanguages()
            langs?.let{
                it.forEach{
                    print("${it.id}:")
                    it.extensions?.forEach {
                        print("$it,")
                    }
                    println()
                }
            }

        }
    }

    fun onButtonAddActionAction() {
        if (monacoEditorFx.editorReadyProperty.value) {
            val action = monacoEditorFx.editor.createAction(
                id = "123456",
                label="Action 1",
                contextMenuGroupId = "navigation"
            ) {
                println("Action: $it")
            }
            actionTest = action
            monacoEditorFx.editor.addAction(action)
        }
    }

    fun onButtonRemoveActionAction() {
        if (monacoEditorFx.editorReadyProperty.value) {
            actionTest?.dispose()
        }
    }

    fun onButtonTest1Action() {
        if(monacoEditorFx.editorReadyProperty.value) {
        }
    }

}
