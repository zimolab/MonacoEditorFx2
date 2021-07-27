package com.zimolab.monacofx.monaco

import netscape.javascript.JSObject

interface IMarkdownString {
    var value: String
    var isTrusted: Boolean?
    var supportThemeIcons: Boolean?
    var uris: JSObject?
}