package com.zimolab.monacofx.jseditor.event

import com.zimolab.monacofx.jsbase.get
import netscape.javascript.JSObject

abstract class KeyboardEvent {
    abstract val altKey: Boolean?
    abstract val bubbles: Boolean?
    abstract val cancelBubble: Boolean?
    abstract val cancelable: Boolean?
    abstract val charCode: Int?
    abstract val code: String?
    abstract val composed: Boolean?
    abstract val ctrlKey: Boolean?
    //abstract val currentTarget: null
    abstract val defaultPrevented: Boolean?
    abstract val detail: Int?
    abstract val eventPhase: Int?
    abstract val isComposing: Boolean?
    abstract val isTrusted: Boolean?
    abstract val key: String?
    abstract val keyCode: Int?
    abstract val location: Int?
    abstract val metaKey: Boolean?
    //abstract val path: (7) [textarea.inputarea.monaco-mouse-cursor-text, div.overflow-guard, div.monaco-editor.no-user-select.showUnused.showDeprecated.vs-dark, body, html, document, Window]
    abstract val repeat: Boolean?
    abstract val returnValue: Boolean?
    abstract val shiftKey: Boolean?
    abstract val timeStamp: Int?
    abstract val type: String?
    abstract val which: Int?

    companion object {
        fun fromJSObject(jsObject: JSObject?): KeyboardEvent? {
            if (jsObject != null) {
                return object : KeyboardEvent() {
                    val obj = jsObject
                    override val altKey: Boolean?
                        get() = obj!!.get("altKey")
                    override val bubbles: Boolean?
                        get() = obj!!.get("bubbles")
                    override val cancelBubble: Boolean?
                        get() = obj!!.get("cancelBubble")
                    override val cancelable: Boolean?
                        get() = obj!!.get("cancelable")
                    override val charCode: Int?
                        get() = obj!!.get("charCode")
                    override val code: String?
                        get() = obj!!.get("code")
                    override val composed: Boolean?
                        get() = obj!!.get("composed")
                    override val ctrlKey: Boolean?
                        get() = obj!!.get("ctrlKey")
                    override val defaultPrevented: Boolean?
                        get() = obj!!.get("defaultPrevented")
                    override val detail: Int?
                        get() = obj!!.get("detail")
                    override val eventPhase: Int?
                        get() = obj!!.get("eventPhase")
                    override val isComposing: Boolean?
                        get() = obj!!.get("isComposing")
                    override val isTrusted: Boolean?
                        get() = obj!!.get("isTrusted")
                    override val key: String?
                        get() = obj!!.get("key")
                    override val keyCode: Int?
                        get() = obj!!.get("keyCode")
                    override val location: Int?
                        get() = obj!!.get("location")
                    override val metaKey: Boolean?
                        get() = obj!!.get("metaKey")
                    override val repeat: Boolean?
                        get() = obj!!.get("repeat")
                    override val returnValue: Boolean?
                        get() = obj!!.get("returnValue")
                    override val shiftKey: Boolean?
                        get() = obj!!.get("shiftKey")
                    override val timeStamp: Int?
                        get() = obj!!.get("timeStamp")
                    override val type: String?
                        get() = obj!!.get("type")
                    override val which: Int?
                        get() = obj!!.get("which")
                }
            }
            return null
        }
    }
}
