package com.zimolab.monacofx.jseditor.event

abstract class IMouseEvent {
    abstract var browserEvent: String?

    abstract val leftButton: Boolean?
    abstract val middleButton: Boolean?
    abstract val rightButton: Boolean
    abstract val buttons: Int?
    abstract val target: String?
    abstract val detail: Int?
    abstract val posx: Int?
    abstract val posy: Int?
    abstract val ctrlKey: Boolean?
    abstract val shiftKey: Boolean?
    abstract val altKey: Boolean?
    abstract val metaKey: Boolean?
    abstract val timestamp: Int?
}

class A : IMouseEvent() {
    var a = 10
    override var browserEvent: String? = ""
        get() = ""
        set(value) {
            field = value
        }
    override val leftButton: Boolean?
        get() {
            if (a > 5) {
                return true
            } else if (a > 10) {
                return false
            } else {
                return null
            }
        }
    override val middleButton: Boolean?
        get() = TODO("Not yet implemented")
    override val rightButton: Boolean
        get() = TODO("Not yet implemented")
    override val buttons: Int?
        get() = TODO("Not yet implemented")
    override val target: String?
        get() = TODO("Not yet implemented")
    override val detail: Int?
        get() = TODO("Not yet implemented")
    override val posx: Int?
        get() = TODO("Not yet implemented")
    override val posy: Int?
        get() = TODO("Not yet implemented")
    override val ctrlKey: Boolean?
        get() = TODO("Not yet implemented")
    override val shiftKey: Boolean?
        get() = TODO("Not yet implemented")
    override val altKey: Boolean?
        get() = TODO("Not yet implemented")
    override val metaKey: Boolean?
        get() = TODO("Not yet implemented")
    override val timestamp: Int?
        get() = TODO("Not yet implemented")

}
