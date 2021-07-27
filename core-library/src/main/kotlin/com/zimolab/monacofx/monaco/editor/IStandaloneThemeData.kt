package com.zimolab.monacofx.monaco.editor

open class IStandaloneThemeData (
    open var base: String, /* "vs" | "vs-dark" | "hc-black" */
    open var inherit: Boolean,
    open var rules: Array<ITokenThemeRule>,
    open var encodedTokensColors: Array<String>? = null,
    open var colors: IColor
)