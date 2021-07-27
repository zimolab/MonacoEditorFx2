package com.zimolab.monacofx.jseditor

interface UriComponents {
    val authority: String?
    val fragment: String?
    val path: String?
    val query: String?
    val scheme: String?
}