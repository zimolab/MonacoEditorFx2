package com.zimolab.monacofx.monaco

abstract class Token(offset: Int, type: String, language: String) {
     abstract var _tokenBrand: Unit
     abstract var offset: Int
     abstract var type: String
     abstract var language: String
}