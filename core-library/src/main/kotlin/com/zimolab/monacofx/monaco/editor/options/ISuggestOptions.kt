package com.zimolab.monacofx.monaco.editor.options

open class ISuggestOptions(
    open val insertMode: String?=null, /* "insert" | "replace" */
    open val filterGraceful: Boolean?=null,
    open val snippetsPreventQuickSuggestions: Boolean?=null,
    open val localityBonus: Boolean?=null,
    open val shareSuggestSelections: Boolean?=null,
    open val showIcons: Boolean?=null,
    open val showStatusBar: Boolean?=null,
    open val preview: Boolean?=null,
    open val previewMode: String?=null, /* "prefix" | "subwordDiff" */
    open val showInlineDetails: Boolean?=null,
    open val showMethods: Boolean?=null,
    open val showFunctions: Boolean?=null,
    open val showConstructors: Boolean?=null,
    open val showDeprecated: Boolean?=null,
    open val showFields: Boolean?=null,
    open val showVariables: Boolean?=null,
    open val showClasses: Boolean?=null,
    open val showStructs: Boolean?=null,
    open val showInterfaces: Boolean?=null,
    open val showModules: Boolean?=null,
    open val showProperties: Boolean?=null,
    open val showEvents: Boolean?=null,
    open val showOperators: Boolean?=null,
    open val showUnits: Boolean?=null,
    open val showValues: Boolean?=null,
    open val showConstants: Boolean?=null,
    open val showEnums: Boolean?=null,
    open val showEnumMembers: Boolean?=null,
    open val showKeywords: Boolean?=null,
    open val showWords: Boolean?=null,
    open val showColors: Boolean?=null,
    open val showFiles: Boolean?=null,
    open val showReferences: Boolean?=null,
    open val showFolders: Boolean?=null,
    open val showTypeParameters: Boolean?=null,
    open val showIssues: Boolean?=null,
    open val showUsers: Boolean?=null,
    open val showSnippets: Boolean?=null,
)