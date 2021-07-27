package com.zimolab.monacofx.jseditor.options

open class IGotoLocationOptions (
    open val multiple: String? = null /* "peek" | "gotoAndPeek" | "goto" */,
    open val multipleDefinitions: String? = null /* "peek" | "gotoAndPeek" | "goto" */,
    open val multipleTypeDefinitions: String? = null /* "peek" | "gotoAndPeek" | "goto" */,
    open val multipleDeclarations: String? = null /* "peek" | "gotoAndPeek" | "goto" */,
    open val multipleImplementations: String? = null /* "peek" | "gotoAndPeek" | "goto" */,
    open val multipleReferences: String? = null /* "peek" | "gotoAndPeek" | "goto" */,
    open val alternativeDefinitionCommand: String? = null,
    open val alternativeTypeDefinitionCommand: String? = null,
    open val alternativeDeclarationCommand: String? = null,
    open val alternativeImplementationCommand: String? = null,
    open val alternativeReferenceCommand: String? = null,
)
