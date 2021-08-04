package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.editor.textmodel.interfaces.ITextModelUpdateOptions

class TextModelUpdateOptions(
    override val tabSize: Int?,
    override val indentSize: Int?,
    override val insertSpaces: Boolean?,
    override val trimAutoWhitespace: Boolean?
): ITextModelUpdateOptions