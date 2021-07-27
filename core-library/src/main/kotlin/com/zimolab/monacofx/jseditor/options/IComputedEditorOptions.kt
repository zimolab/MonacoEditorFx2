package com.zimolab.monacofx.jseditor.options

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.lang.IllegalArgumentException

open class IComputedEditorOptions {
    companion object {
        const val KEY = "_values"
    }

    val values: JSONArray

    constructor(values: JSONArray) {
        this.values = values
    }

    constructor(jsonStr: String) {
        val json = JSON.parse(jsonStr) as? JSONObject
        if (json!=null && KEY in json && json[KEY] is JSONArray) {
            values = json[KEY] as JSONArray
        } else {
            throw IllegalArgumentException()
        }
    }

    inline fun <reified T> get(id: Int): T? {
        if (id >= values.count())
            return null
        return values.getObject(id, T::class.java)
    }
}

// Test Code
//fun main() {
//    val js = "{\"_values\":[true,\"on\",0,10,\"Editor content;Press Alt+F1 for Accessibility Options.\",\"languageDefined\",\"auto\",\"auto\",\"languageDefined\",3,false,\"languageDefined\",true,\"\",0,true,false,{\"insertSpace\":true,\"ignoreEmptyLines\":true},true,true,1,false,1,0,\"default\",0,false,false,false,true,true,\"\",5,{\"cursorMoveOnType\":true,\"seedSearchStringFromSelection\":true,\"autoFindInSelection\":\"never\",\"globalFindClipboard\":false,\"addExtraSpaceOnTop\":true,\"loop\":true},false,true,\"auto\",true,false,\"Consolas, 'Courier New', monospace\",{\"zoomLevel\":0,\"pixelRatio\":1.25,\"fontFamily\":\"Consolas, 'Courier New', monospace\",\"fontWeight\":\"normal\",\"fontSize\":14,\"fontFeatureSettings\":\"\\\"liga\\\" off, \\\"calt\\\" off\",\"lineHeight\":19,\"letterSpacing\":0,\"version\":1,\"isTrusted\":true,\"isMonospace\":true,\"typicalHalfwidthCharacterWidth\":7.6953125,\"typicalFullwidthCharacterWidth\":14.3984375,\"canUseHalfwidthRightwardsArrow\":false,\"spaceWidth\":7.6953125,\"middotWidth\":7.6953125,\"wsmiddotWidth\":4.23828125,\"maxDigitWidth\":7.6953125},\"\\\"liga\\\" off, \\\"calt\\\" off\",14,\"normal\",false,false,false,{\"multiple\":\"peek\",\"multipleDefinitions\":\"peek\",\"multipleTypeDefinitions\":\"peek\",\"multipleDeclarations\":\"peek\",\"multipleImplementations\":\"peek\",\"multipleReferences\":\"peek\",\"alternativeDefinitionCommand\":\"editor.action.goToReferences\",\"alternativeTypeDefinitionCommand\":\"editor.action.goToReferences\",\"alternativeDeclarationCommand\":\"editor.action.goToReferences\",\"alternativeImplementationCommand\":\"\",\"alternativeReferenceCommand\":\"\"},false,true,{\"enabled\":true,\"delay\":300,\"sticky\":true},false,{\"enabled\":false,\"mode\":\"subwordDiff\"},0,{\"enabled\":true},10,19,{\"renderType\":1,\"renderFn\":null},5,false,true,\"always\",{\"enabled\":true,\"size\":\"proportional\",\"side\":\"right\",\"showSlider\":\"mouseover\",\"renderCharacters\":true,\"maxColumn\":120,\"scale\":1},\"text\",1,false,true,\"altKey\",\"spread\",true,true,2,{\"top\":0,\"bottom\":0},{\"enabled\":true,\"cycle\":false},\"tree\",false,{\"other\":true,\"comments\":false,\"strings\":false},10,false,false,false,true,true,\"line\",false,\"editable\",\"selection\",30,true,[],{\"vertical\":1,\"horizontal\":1,\"arrowSize\":11,\"useShadows\":true,\"verticalHasArrows\":false,\"horizontalHasArrows\":false,\"horizontalScrollbarSize\":12,\"horizontalSliderSize\":12,\"verticalScrollbarSize\":14,\"verticalSliderSize\":14,\"handleMouseWheel\":true,\"alwaysConsumeMouseWheel\":true,\"scrollByPage\":false},5,true,true,true,true,true,\"mouseover\",true,\"inline\",{\"selectLeadingAndTrailingWhitespace\":true},false,false,10000,{\"insertMode\":\"insert\",\"filterGraceful\":true,\"snippetsPreventQuickSuggestions\":true,\"localityBonus\":false,\"shareSuggestSelections\":false,\"showIcons\":true,\"showStatusBar\":false,\"preview\":false,\"previewMode\":\"prefix\",\"showInlineDetails\":true,\"showMethods\":true,\"showFunctions\":true,\"showConstructors\":true,\"showDeprecated\":true,\"showFields\":true,\"showVariables\":true,\"showClasses\":true,\"showStructs\":true,\"showInterfaces\":true,\"showModules\":true,\"showProperties\":true,\"showEvents\":true,\"showOperators\":true,\"showUnits\":true,\"showValues\":true,\"showConstants\":true,\"showEnums\":true,\"showEnumMembers\":true,\"showKeywords\":true,\"showWords\":true,\"showColors\":true,\"showFiles\":true,\"showReferences\":true,\"showFolders\":true,\"showTypeParameters\":true,\"showSnippets\":true,\"showUsers\":true,\"showIssues\":true},0,0,true,\"recentlyUsed\",\"off\",0,\"prompt\",true,true,\"`~!@#$%^&*()-=+[{]}\\\\|;:'\\\",.<>/?\",\"off\",\" \\t})]?|/&.,;¢°′″‰℃、。｡､￠，．：；？！％・･ゝゞヽヾーァィゥェォッャュョヮヵヶぁぃぅぇぉっゃゅょゎゕゖㇰㇱㇲㇳㇴㇵㇶㇷㇸㇹㇺㇻㇼㇽㇾㇿ々〻ｧｨｩｪｫｬｭｮｯｰ”〉》」』】〕）］｝｣\",\"([{‘“〈《「『【〔（［｛｢£¥＄￡￥+＋\",80,\"inherit\",\"inherit\",0,\"simple\",true,{\"enabled\":true,\"fontSize\":0,\"fontFamily\":\"Consolas, 'Courier New', monospace\"},\"monaco-editor no-user-select  showUnused showDeprecated\",1.25,false,{\"width\":752,\"height\":344,\"glyphMarginLeft\":0,\"glyphMarginWidth\":0,\"lineNumbersLeft\":0,\"lineNumbersWidth\":38,\"decorationsLeft\":38,\"decorationsWidth\":26,\"contentLeft\":64,\"contentWidth\":617,\"minimap\":{\"renderMinimap\":1,\"minimapLeft\":667,\"minimapWidth\":71,\"minimapHeightIsEditorHeight\":false,\"minimapIsSampling\":false,\"minimapScale\":1,\"minimapLineHeight\":2,\"minimapCanvasInnerWidth\":88,\"minimapCanvasInnerHeight\":430,\"minimapCanvasOuterWidth\":70.4,\"minimapCanvasOuterHeight\":344},\"viewportColumn\":78,\"isWordWrapMinified\":false,\"isViewportWrapping\":false,\"wrappingColumn\":-1,\"verticalScrollbarWidth\":14,\"horizontalScrollbarHeight\":12,\"overviewRuler\":{\"top\":0,\"width\":14,\"height\":344,\"right\":0}},{\"isDominatedByLongLines\":false,\"isWordWrapMinified\":false,\"isViewportWrapping\":false,\"wrappingColumn\":-1}]}"
//    val json: JSONObject = JSON.parse(js) as JSONObject
//    json["_values"]?.let {
//        if (it is JSONArray) {
//            val tmp = IComputedEditorOptions(it)
//            val a = tmp.get<IEditorFindOptions>(EditorOption.find.id)
//            println(a?.autoFindInSelection)
//        }
//    }
//}