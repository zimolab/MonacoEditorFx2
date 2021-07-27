package com.zimolab.monacofx.jseditor.options

open class ITokenThemeRule(
    open val token: String,
    open val foreground: String? = null,
    open val background: String? = null,
    open val fontStyle: String? = null,
)