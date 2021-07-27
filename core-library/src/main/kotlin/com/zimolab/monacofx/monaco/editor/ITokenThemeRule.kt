package com.zimolab.monacofx.monaco.editor

open class ITokenThemeRule(
    open val token: String,
    open val foreground: String? = null,
    open val background: String? = null,
    open val fontStyle: String? = null,
)