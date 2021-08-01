package com.zimolab.monacofx.monaco.editor.textmodel

import com.zimolab.monacofx.monaco.IMarkdownString

interface IModelDecorationOptions {
    var stickiness: Int?
    var className: String?
    var glyphMarginHoverMessage: IMarkdownString? /* IMarkdownString? | Array<IMarkdownString>? */
    var hoverMessage: IMarkdownString? /* IMarkdownString? | Array<IMarkdownString>? */
    var isWholeLine: Boolean?
    var zIndex: Number?
    var overviewRuler: IModelDecorationOverviewRulerOptions?
    var minimap: IModelDecorationMinimapOptions?
    var glyphMarginClassName: String?
    var linesDecorationsClassName: String?
    var firstLineDecorationClassName: String?
    var marginClassName: String?
    var inlineClassName: String?
    var inlineClassNameAffectsLetterSpacing: Boolean?
    var beforeContentClassName: String?
    var afterContentClassName: String?
    var after: InjectedTextOptions?
    var before: InjectedTextOptions?
}