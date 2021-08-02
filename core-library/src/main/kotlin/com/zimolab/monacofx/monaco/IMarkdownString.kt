package com.zimolab.monacofx.monaco

import netscape.javascript.JSObject

interface IMarkdownString {
    val value: String
    val isTrusted: Boolean?
    val supportThemeIcons: Boolean?
    val uris: JSObject?
}