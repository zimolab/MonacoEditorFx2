package com.zimolab.monacofx.demo.textmodel

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.zimolab.monacofx.MonacoEditorFx
import com.zimolab.monacofx.monaco.IRange
import com.zimolab.monacofx.monaco.languages.ILanguageExtensionPoint
import javafx.scene.control.*
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import tornadofx.*
import java.time.LocalDateTime.now

class TextModelDemoView: View("TextModel API Demo") {
    override val root: VBox by fxml("/view/TextModelDemoView.fxml")
    lateinit var languages: List<ILanguageExtensionPoint>
    val onContentChangeCheckBox by fxid<CheckBox>()
    val leftStatusLabel by fxid<Label>()
    val centerStatusLabel by fxid<Label>()
    val rightStatusLabel by fxid<Label>()
    val outputTextArea by fxid<TextArea>()
    val plainTextArea by fxid<TextArea>()
    val centerPane by fxid<VBox>()
    val languageChoiceBox by fxid<ChoiceBox<ILanguageExtensionPoint>>()
    val themeChoiceBox by fxid<ChoiceBox<String>>()
    val monacoEditorFx: MonacoEditorFx by lazy {
        MonacoEditorFx()
    }

    init {

        monacoEditorFx.editorReadyProperty.addListener { observable, oldValue, newValue ->
            onMonacoEditorReady()
        }

        monacoEditorFx.loadStateProperty.addListener { observable, oldValue, newValue ->
            onMonacoEditorLoadStateChange(newValue)
        }

        monacoEditorFx.internalErrorProperty.addListener { observable, oldValue, newValue ->
            onMonacoEditorInternalErrorOccurs(newValue)
        }

        leftStatusLabel.text = ""
        rightStatusLabel.text = ""
        centerStatusLabel.text = ""

        centerPane.children.add(monacoEditorFx)
        monacoEditorFx.vgrow = Priority.ALWAYS
        centerPane.vgrow = Priority.ALWAYS

        outputTextArea.editableProperty().set(false)
        log("初始化...")
    }

    // API: editor.reload()
    fun onReloadButtonAction() {
        monacoEditorFx.reload()
    }

    // API:
    // textModel.getOptions()
    fun onGetOptionsButtonAction() {
        editorReady {
            val options = monacoEditorFx.editor.textModel?.getOptions()
            options?.let {
                log("getOptions(): ${JSON.toJSONString(it, SerializerFeature.PrettyFormat)}")
            }?: log("调用失败: getOptions()")
        }
    }

    // API:
    // textModel.getVersionId()
    // textModel.getAlternativeVersionId()
    fun onGetVersionIdButtonAction() {
        editorReady {
            val versionId = monacoEditorFx.editor.textModel?.getVersionId()
            val alternativeVersionId = monacoEditorFx.editor.textModel?.getAlternativeVersionId()
            log("getVersionId(): ${versionId}, getAlternativeVersionId(): $alternativeVersionId")
        }
    }

    // API: textModel.getValue()
    fun onGetValueButtonAction() {
        editorReady {
            val text = monacoEditorFx.editor.textModel?.getValue(preserveBOM = true)
            log("getValue() (是否调用成功：${text != null})")
            text?.let {
                plainTextArea.text = it
            }
        }
    }

    // API: textModel.setValue()
    fun onSetValueButtonAction() {
        editorReady {
            val text = plainTextArea.text
            val r = monacoEditorFx.editor.textModel?.setValue(text)
            log("setValue() (是否调用成功：${r})")
        }
    }

    // API:
    // editor.getSelection()
    // textModel.getValueInRange()
    fun onGetInfoButtonAction() {
        editorReady {
            monacoEditorFx.editor.getSelection()?.let { selection ->
                log("editor.getSelection(): $selection")
                val selectedRange = selection.toRange()
                val selectedValue = monacoEditorFx.editor.textModel?.getValueInRange(selectedRange)
                log("""textModel.getValueInRange(): "$selectedValue"($selectedRange)""")
            }
        }
    }

    fun onContentChangeCheckBoxAction() {

    }

    /**
     * 监听编辑器状态变化
     * @param newValue LoadState
     */
    fun onMonacoEditorLoadStateChange(newValue: MonacoEditorFx.LoadState) {

        log("编辑器加载状态发生变化: $newValue")
        leftStatusLabel.text = newValue.name

        // 当编辑器已经加载完成时
        if (newValue == MonacoEditorFx.LoadState.LOADED) {
            languageChoiceBox.items.clear()
            themeChoiceBox.items.clear()
            languageApisDemo()
            themeApisDemo()
        }

    }

    /**
     * 演示与language有关的API，包括：
     * 1、获取编辑器支持的全部语言
     * 2、获取当前语言
     * 3、监听语言切换事件
     * 4、切换当前语言
     */
    private fun languageApisDemo() {

        // 1、获取编辑器支持的全部语言
        // 测试API：MonacoEditor.getLanguages()
        log("获取受支持的语言...")
        languages = when (val r = monacoEditorFx.editor.getLanguages()) {
            null -> arrayListOf()
            else -> r
        }
        log("发现 ${languages.size} 种受支持的的语言")

        // 将获取到的语言添加到choice box中
        object : StringConverter<ILanguageExtensionPoint>() {
            override fun toString(language: ILanguageExtensionPoint?): String {
                return language?.id ?: ""
            }

            override fun fromString(string: String?): ILanguageExtensionPoint? {
                if (string == null)
                    return null
                return languages.find { it.id == string }
            }
        }.also { languageChoiceBox.converter = it }
        languages.forEach { languageChoiceBox.items.add(it) }

        // 2、获取当前语言
        // 测试API：MonacoEditor.getCurrentLanguage()
        val currentLanguage = monacoEditorFx.editor.getCurrentLanguage()
        currentLanguage?.let {
            updateCurrentLanguage(it)
        }

        // 3、监听编辑器语言变化
        // 演示API: TextModel.onDidChangeLanguage()
        monacoEditorFx.editor.textModel?.onDidChangeLanguage { eventId, event ->
            updateCurrentLanguage(event.newLanguage)
        }

        // 4、切换当前语言
        // 演示API: MonacoEditor.setCurrentLanguage()
        // 为choice box设置监听，演示编辑器语言切换功能
        languageChoiceBox.selectionModel.selectedItemProperty().addListener { _, _, current ->
            current?.let {
                monacoEditorFx.editor.setCurrentLanguage(it.id)
            }
        }
    }

    /**
     * 演示与主题有关的API，包括：
     * 1、获取内置主题以及当前主题
     * 2、切换当前主题
     * TODO 3、自定义主题
     */
    fun themeApisDemo() {
        //1、获取内置主题以及当前主题
        // API: editor.getBuiltinThemes、editor.getTheme()
        log("获取内置主题...")
        val builtinThemes = monacoEditorFx.editor.getBuiltinThemes()
        builtinThemes?.let {
            log("发现 ${it.length} 种内置主题: ${it.join("、")}")
        }
        log("获取当前主题...")
        val theme = monacoEditorFx.editor.getTheme()
        theme?.let { log("当前主题为： $it") }
        builtinThemes?.forEach { _, item ->
            if (item is String)
                themeChoiceBox.items.add(item)
        }
        theme?.let { themeChoiceBox.selectionModel.select(it) }

        // 2、切换当前主题
        // API: editor.setTheme()
        themeChoiceBox.selectionModel.selectedItemProperty().addListener { _, oldTheme, newTheme ->
            newTheme?.let {
                log("设置当前主题...")
                if(monacoEditorFx.editor.setTheme(it)) {
                    log("当前主题为：$it")
                }
            }
        }

        // TODO 3、自定义主题

    }

    fun onMonacoEditorReady() {
        log("编辑器准备就绪")
        leftStatusLabel.text = "Ready"
    }

    fun onMonacoEditorInternalErrorOccurs(newValue: Pair<String, Throwable>?) {
        log("发生了一个内部错误(${newValue?.first} : ${newValue?.second})")
        leftStatusLabel.text = "Error"
        centerStatusLabel.text = newValue?.first
    }

    private fun log(msg: String) {
        outputTextArea.appendText("[${now()}] $msg\n")
    }

    private fun clearOutput() {
        outputTextArea.clear()
    }

    private fun updateCurrentLanguage(currentLanguage: String) {
        log("当前语言为: $currentLanguage")
        languageChoiceBox.selectionModel.select(languageChoiceBox.converter.fromString(currentLanguage))
        rightStatusLabel.text = currentLanguage
    }

    fun editorReady(block: ()->Unit) {
        if (monacoEditorFx.editorReadyProperty.value)
            block()
        else
            log("编辑器尚未就绪！")
    }
}