package com.zimolab.monacofx.jseditor.event

import com.zimolab.monacofx.jsbase.get
import com.zimolab.monacofx.jsbase.invoke
import netscape.javascript.JSObject

abstract class IKeyboardEvent {
    abstract val _standardKeyboardEventBrand: Boolean?
    abstract val browserEvent: KeyboardEvent?
    abstract val ctrlKey: Boolean?
    abstract val shiftKey: Boolean?
    abstract val altKey: Boolean?
    abstract val metaKey: Boolean?
    abstract val keyCode: Int?
    abstract val code: String?
    abstract fun equals(keybinding: Int): Boolean?
    abstract fun preventDefault()
    abstract fun stopPropagation()

    companion object {
        fun fromJSObject(jsObject: JSObject?): IKeyboardEvent? {
            if (jsObject != null) {
                return object : IKeyboardEvent() {
                    val obj = jsObject
                    override val _standardKeyboardEventBrand: Boolean?
                        get() = obj!!.get("_standardKeyboardEventBrand")
                    override val browserEvent: KeyboardEvent?
                        get() = KeyboardEvent.fromJSObject(jsObject.get<JSObject>("browserEvent"))
                    override val ctrlKey: Boolean?
                        get() = obj!!.get("ctrlKey")
                    override val shiftKey: Boolean?
                        get() = obj!!.get("shiftKey")
                    override val altKey: Boolean?
                        get() = obj!!.get("altKey")
                    override val metaKey: Boolean?
                        get() = obj!!.get("metaKey")
                    override val keyCode: Int?
                        get() = obj!!.get("keyCode")
                    override val code: String?
                        get() = obj!!.get("code")

                    override fun equals(keybinding: Int): Boolean {
                        return obj!!.invoke("equals", keybinding) == true
                    }

                    override fun preventDefault() {
                        obj!!.invoke("preventDefault")
                    }

                    override fun stopPropagation() {
                        obj!!.invoke("stopPropagation")
                    }
                }
            }
            return null
        }
    }
}