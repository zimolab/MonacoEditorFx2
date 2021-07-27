package com.zimolab.monacofx.jsbase


import com.zimolab.monacofx.monaco.Globals.JS_EDITOR_NAMESPACE
import javafx.scene.web.WebEngine

// 相当于在java层创建并持有的一个原生JS对象的引用
interface JsVariableReference {
    companion object {
        const val JS_OBJECT_CREATOR_ID = "$JS_EDITOR_NAMESPACE.ObjectCreator"
        const val JS_OBJECTS_SCOPE = "$JS_EDITOR_NAMESPACE.__\$\$OBJECTS\$\$__"

        fun init(webEngine: WebEngine) {
            webEngine.executeScript("$JS_OBJECTS_SCOPE={};")
        }

        fun createVarCodeTemplate(reference: JsVariableReference): String {
            val jsReference = "$JS_OBJECTS_SCOPE[\"${reference.jsVariableName()}\"]"
            return "$jsReference=$JS_OBJECT_CREATOR_ID.${reference.jsClassName()}(%s);"
        }

        fun deleteVarCodeTemplate(reference: JsVariableReference): String {
            val jsReference = "$JS_OBJECTS_SCOPE[\"${reference.jsVariableName()}\"]"
            return "delete $jsReference;"
        }
    }

    fun jsClassName(): String
    fun jsVariableName(): String
    fun reference(): String?
    fun delete(): Boolean
}