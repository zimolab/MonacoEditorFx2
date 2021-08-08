package com.zimolab.monacofx.util

import com.zimolab.monacofx.jsbase.JsBridge
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

object Clipboard: JsBridge {
    const val NAME_IN_JS = "javaClipboard"
    const val DESCRIPTION = ""

    private val sysClipboard = Toolkit.getDefaultToolkit().systemClipboard

    override fun getJavascriptName(): String = NAME_IN_JS
    override fun getDescription(): String = DESCRIPTION

    fun getContent(): String? {

        val content = sysClipboard.getContents(null)?.let {
            if (it.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                it.getTransferData(DataFlavor.stringFlavor) as? String
            } else {
               null
            }
        }
        return content
    }

    fun setContent(content: String) {
        sysClipboard.setContents(StringSelection(content), null)
    }

    fun clearClipboard() {
        setContent("")
    }

}