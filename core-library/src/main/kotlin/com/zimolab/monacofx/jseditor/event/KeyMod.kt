package com.zimolab.monacofx.jseditor.event

// https://github.com/microsoft/vscode/blob/main/src/vs/base/common/keyCodes.ts#L395
object KeyMod {
    var CtrlCmd: Int = (1 shl 11) ushr 0
    var Shift: Int = (1.shl(10)) ushr 0
    var Alt: Int = (1.shl(9)) ushr 0
    var WinCtrl: Int = (1.shl(8)) ushr 0
    fun chord(firstPart: Int, secondPart: Int): Int {
        val chordPart = ((secondPart and 0x0000FFFF) shl 16) ushr 0
        return (firstPart or chordPart) ushr 0
    }
}