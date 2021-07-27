package com.zimolab.monacofx.jsbase

import com.zimolab.monacofx.jseditor.Globals.JS_UNDEFINED
import javafx.scene.web.WebEngine
import netscape.javascript.JSException
import netscape.javascript.JSObject

private val ID_REGEXP = """^[a-zA-Z_$][a-zA-Z0-9_$]*$""".toRegex()
private val ES_KEYWORDS = setOf(
    "break", "case", "catch", "continue", "default", "delete", "do",
    "else", "finally", "for", "function", "if", "in", "instanceof",
    "new", "return", "switch", "this", "throw", "try", "typeof", "var",
    "void", "while", "with", "abstract", "boolean", "byte", "char",
    "class", "const", "debugger", "double", "enum", "export", "extends",
    "final", "float", "goto", "implements", "import", "int", "interface",
    "long", "native", "package", "private", "protected", "public", "short",
    "static", "super", "synchronized", "throws", "transient", "volatile"
)

fun JSObject.inject(javaObject: JsBridge) {
    val jsName = javaObject.getJavascriptName()
    if (!ID_REGEXP.matches(jsName) || jsName in ES_KEYWORDS)
        throw IllegalArgumentException("Illegal javascript name of java object")
    this.setMember(javaObject.getJavascriptName(), javaObject)
}

fun JSObject.hasMember(javaObject: JsBridge): Boolean {
    return this.getMember(javaObject.getJavascriptName()) != JS_UNDEFINED
}

inline fun <reified T> JSObject.get(name: String): T? {
    return this.getMember(name) as T
}

fun JSObject.remove(javaObject: JsBridge) {
    this.removeMember(javaObject.getJavascriptName())
}

fun JSObject.invoke(methodName: String, vararg args: Any?, silently: Boolean = true, printlnStackTrace: Boolean = true): Any {
    return if (silently) {
        try {
            this.call(methodName, *args)
        } catch (e: JSException) {
            if (printlnStackTrace) e.printStackTrace()
            e
        }
    } else {
        this.call(methodName, args)
    }
}

fun WebEngine.execute(jsCode: String, silently: Boolean = true, printlnStackTrace: Boolean = true): Any {
    return if (silently) {
        try {
           this.executeScript(jsCode)
        } catch (e: JSException) {
          if (printlnStackTrace) e.printStackTrace()
          e
        }
    } else {
        this.executeScript(jsCode)
    }
}