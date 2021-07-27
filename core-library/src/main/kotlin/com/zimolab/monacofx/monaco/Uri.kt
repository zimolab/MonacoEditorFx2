package com.zimolab.monacofx.monaco

open class Uri(
    override var authority: String? = null,
    override var fragment: String? = null,
    override var path: String? = null,
    override var query: String? = null,
    override var scheme: String? = null,
) : UriComponents
