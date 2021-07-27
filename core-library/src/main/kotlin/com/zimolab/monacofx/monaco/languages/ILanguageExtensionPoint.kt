package com.zimolab.monacofx.monaco.languages

import com.zimolab.monacofx.monaco.Uri

open class ILanguageExtensionPoint(
    var id: String,
    var extensions: Array<String>? = null,
    var filenames: Array<String>? = null,
    var filenamePatterns: Array<String>? = null,
    var firstLine: String? = null,
    var aliases: Array<String>? = null,
    var mimetypes: Array<String>? = null,
    var configuration: Uri? = null,
)