package com.zimolab.monacofx.monaco.editor

interface FontInfo: BareFontInfo {
     val _editorStylingBrand: Unit
     val version: Int
     val isTrusted: Boolean
     val isMonospace: Boolean
     val typicalHalfwidthCharacterWidth: Int
     val typicalFullwidthCharacterWidth: Int
     val canUseHalfwidthRightwardsArrow: Boolean
     val spaceWidth: Int
     val middotWidth: Int
     val wsmiddotWidth: Int
     val maxDigitWidth: Int
}