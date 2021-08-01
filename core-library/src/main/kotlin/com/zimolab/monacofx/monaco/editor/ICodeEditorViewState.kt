package com.zimolab.monacofx.monaco.editor

import com.zimolab.jsobject.annotations.JsField
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.monacofx.jsbase.JsArray
import com.zimolab.monacofx.monaco.Position
import netscape.javascript.JSObject

@JsInterface
interface ICodeEditorViewState {
    val cursorState: Any
    val viewState: Any
    val contributionsState: Any
}

@JsInterface
interface ICursorState  {
    val inSelectionMode: Boolean
    val selectionStart: Any
    val position: Any
}

@JsInterface
interface IViewState {
    @JsField(ignoreUndefined = true)
    val scrollTop: Int?
    @JsField(ignoreUndefined = true)
    val scrollTopWithoutViewZones: Int?
    val scrollLeft: Int
    val firstPosition: Any
    val firstPositionDeltaTop: Int
}

class CodeEditorViewState(jsObject: JSObject): AbstractICodeEditorViewState(jsObject) {
    private val _cursorState by lazy{
        JsArray(super.cursorState as JSObject)
    }

    private val _viewState by lazy{
        ViewState(super.viewState as JSObject)
    }

    private val _contributionsState by lazy{
        super.contributionsState as JSObject
    }

    override val cursorState: JsArray
        get() = _cursorState
    override val viewState: ViewState
        get() = _viewState
    override val contributionsState: JSObject
        get() = _contributionsState
}

class ViewState(jsObject: JSObject): AbstractIViewState(jsObject) {
    private val _firstPosition by lazy {
        Position(super.firstPosition as JSObject)
    }

    override val firstPosition: Any
        get() = _firstPosition
}

class CursorState(jsObject: JSObject): AbstractICursorState(jsObject) {
    private val _selectionStart by lazy {
        Position(super.selectionStart as JSObject)
    }

    private val _position by lazy {
        Position(super.position as JSObject)

    }

    override val selectionStart: Position
        get() = _selectionStart
    override val position: Position
        get() = _position
}