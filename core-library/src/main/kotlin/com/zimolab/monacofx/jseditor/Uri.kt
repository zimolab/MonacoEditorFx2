package com.zimolab.monacofx.jseditor

open class Uri(
    override val authority: String? = null,
    override val fragment: String? = null,
    override val path: String? = null,
    override val query: String? = null,
    override val scheme: String? = null,
) : UriComponents
