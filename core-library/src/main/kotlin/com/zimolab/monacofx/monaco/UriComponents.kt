package com.zimolab.monacofx.monaco

interface UriComponents {
    var authority: String?
    var fragment: String?
    var path: String?
    var query: String?
    var scheme: String?
}