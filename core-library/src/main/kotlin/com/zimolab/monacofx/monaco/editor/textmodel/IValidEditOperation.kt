package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.Range

interface IValidEditOperation {
    var range: Range
    var text: String
}