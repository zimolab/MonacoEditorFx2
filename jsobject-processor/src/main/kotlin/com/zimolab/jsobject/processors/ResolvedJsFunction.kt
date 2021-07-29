package com.zimolab.jsobject.processors

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.zimolab.jsobject.annotations.JsFunction
import com.zimolab.jsobject.findArgument
import com.zimolab.jsobject.qualifiedNameStr
import com.zimolab.jsobject.simpleNameStr
import kotlin.collections.ArrayList

/**
 * 代表一个被@JsFunction标记的函数，同时包含了函数本身的信息以及注解中所携带的信息
 * @property function KSFunctionDeclaration 函数源对象
 * @property annotation KSAnnotation 注解源对象
 * @property functionName String 函数名
 * @property jsFunctionName String 在Js中的函数名
 * @property qualifiedName String 函数的全称
 * @property returnType KSType 函数返回值
 * @property exceptionOnUndefined Boolean 返回undefined时是否抛出异常
 * @property parameters MutableMap<String, Pair<KSType, Boolean>> 参数列表
 * @constructor
 */
class ResolvedJsFunction(
    val function: KSFunctionDeclaration,
    val annotation: KSAnnotation
) {

    data class JsFunctionParameter(
        val name: String,
        val type: KSType,
        val isVararg: Boolean
    )

    val functionName: String by lazy {
        function.simpleNameStr
    }

    val jsFunctionName: String by lazy {
        annotation.findArgument(JsFunction::jsFunctionName.name, functionName)
    }

    val qualifiedName: String by lazy {
        function.qualifiedNameStr
    }

    val returnType: KSType by lazy {
        function.returnType?.resolve()
            ?: throw AnnotationProcessingError("an error occurs during resolve the return type of function '${functionName}'")
    }

    val exceptionOnUndefined: Boolean by lazy {
        annotation.findArgument(JsFunction::exceptionOnUndefined.name, JsFunction.EXCEPTION_ON_UNDEFINED)
    }

    // 参数名 -> (参数类型，是否为vararg)
    val parameters: List<JsFunctionParameter> by lazy {
        val params = ArrayList<JsFunctionParameter>()
        function.parameters.forEach { parameter ->
            val name = parameter.name?.asString()
                ?: throw AnnotationProcessingError("a parameter name of function '$functionName' is null")
            params.add(JsFunctionParameter(name, parameter.type.resolve(), parameter.isVararg))
        }
        params
    }
}
