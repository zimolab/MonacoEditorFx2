package com.zimolab.monacofx.monaco

import com.zimolab.jsobject.annotations.JsInterface

@JsInterface
interface UriComponents {
    val authority: String?
    val fragment: String?
    val path: String?
    val query: String?
    val scheme: String?
}