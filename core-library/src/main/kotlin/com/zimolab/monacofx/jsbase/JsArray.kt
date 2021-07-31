package com.zimolab.monacofx.jsbase

import netscape.javascript.JSObject

open class JsArray(val source: JSObject) {

    companion object {
        const val JSCODE_IS_JS_ARRAY = "Object.prototype.toString.apply(%s) === '[object Array]'"

        fun isJsArray(jsObject: JSObject): Boolean {
            return jsObject.eval(JSCODE_IS_JS_ARRAY.format("this")) == true
        }

        inline fun inject(source: JSObject, method: String, callback: Any, execution: (JSObject, method: String)->Any?): Any? {
            val methodName = "__$method"
            source.setMember(methodName, callback)
            val result = execution(source, "this.${methodName}.call")
            source.removeMember(methodName)
            return result
        }
    }

    init {
        if (!isJsArray(source))
            throw NotAnJsArrayException("the source object is not an js Array")
    }

    open val length: Int
        get() {
            val length = source.getMember("length")
            if (length !is Int) {
                println("length is undefined")
                throw RuntimeException("failed to get length from a js object")
            }
            return length
        }

    open operator fun get(index: Int): Any? {
        return when(val result = source.getSlot(index)) {
            "undefined" -> Undefined
            else -> result
        }
    }

    open operator fun set(index: Int, value: Any?) {
        if (value is Undefined)
            source.setSlot(index, "undefined")
        else
            source.setSlot(index, value)
    }

    fun concat(other: JsArray): JsArray {
        val result = source.call("concat", other.source)
        return if (result is JSObject)
            JsArray(result)
        else
            throw JsFunctionInvokeException("failed to invoke function 'concat()' on a js Array object")
    }

    fun concat(other: JSObject): JsArray {
        return concat(JsArray(other))
    }

    fun join(separator: String = ","): String {
        val result = source.call("join", separator)
        return if (result is String)
            result
        else
            throw JsFunctionInvokeException("failed to invoke function 'join()' on a js Array object")

    }

    fun pop(): Any? {
        return source.call("pop")
    }

    fun push(vararg elements: Any?): Int {
        val result = source.call("push", *elements)
        return if (result is Int)
            result
        else
            throw JsFunctionInvokeException("failed to invoke function 'push()' on a js Array object")
    }

    fun shift(): Any? {
        return source.call("shift")
    }

    fun unshift(vararg elements: Any?): Int {
        val result = source.call("unshift", *elements)
        return if (result is Int)
            result
        else
            throw JsFunctionInvokeException("failed to invoke function 'unshift()' on a js Array object")
    }

    fun slice(start: Int, end: Int? = null): JsArray {
        val result = if (end == null)
            source.call("slice", start)
        else
            source.call("slice", start, end)
        return if (result is JSObject)
            JsArray(result)
        else
            throw  JsFunctionInvokeException("failed to invoke function 'slice()' on a js Array object")
    }

    fun reverse(): JsArray {
        val result = source.call("reverse")
        return if (result is JSObject)
            JsArray(result)
        else
            throw  JsFunctionInvokeException("failed to invoke function 'reverse()' on a js Array object")

    }

    fun splice(index: Int, count: Int, vararg items: Any?): JsArray {
        val result = source.call("splice", index, count, *items)
        return if (result is JSObject)
            JsArray(result)
        else
            throw  JsFunctionInvokeException("failed to invoke function 'splice()' on a js Array object")

    }

    fun fill(value: Any?, start: Int = 0, end: Int? = null): JsArray {
        val result = if (end == null) {
            source.call("fill", value, start)
        } else {
            source.call("fill", value, start, end)
        }

        return if (result is JSObject)
            JsArray(result)
        else
            throw  JsFunctionInvokeException("failed to invoke function 'fill()' on a js Array object")

    }

    fun find(callback: JsArrayFindCallback): Any? {
        val result = inject(source, "find", callback) { source, method->
            source.eval("this.find((item, index, arr)=>{ return ${method}(item, index, arr) })")
        }
        return result
    }

    fun findIndex(callback: JsArrayFindCallback): Int {
        val result = inject(source, "findIndex", callback) { source, method->
            source.eval("this.findIndex((item, index, arr)=>{ return ${method}(item, index, arr) })")
        }
        return if (result is Int)
            result
        else
            throw  JsFunctionInvokeException("failed to invoke function 'findIndex()' on a js Array object")
    }

    fun includes(ele: Any?, start: Int = 0): Boolean {
        return source.call("includes", ele, start) == true
    }

    fun indexOf(ele: Any?, start: Int = 0): Int {
        val result = source.call("indexOf", ele, start)
        return if (result is Int)
            result
        else
            throw  JsFunctionInvokeException("failed to invoke function 'indexOf()' on a js Array object")
    }

    fun lastIndexOf(ele: Any?, start: Int = -1): Int {
        val result = source.call("lastIndexOf", ele, start)
        return if (result is Int)
            result
        else
            throw  JsFunctionInvokeException("failed to invoke function 'lastIndexOf()' on a js Array object")
    }

    // 以下迭代方法
    fun forEach(callback: (Int, Any?) -> Unit) {
        val range = (0 until length)
        for (i in range) {
            callback(i, source.getSlot(i))
        }
    }

    fun jsForEach(callback: JsArrayForEachCallback) {
        inject(source, "forEach", callback) { source, method->
            source.eval("this.forEach((item, index, arr)=>{ ${method}(index, item) })")
        }
    }

    fun jsFor(callback: JsArrayForCallback, startIndex: Int = 0, stopIndex: Int = -1, step: Int = 1) {
        val _stopIndex = if (stopIndex <= 0)
            length
        else
            stopIndex
        inject(source, "for", callback) { source, method ->
            val jsCode = """
            for(let i=${startIndex}; i < ${_stopIndex}; i = i + $step) {
                if(!${method}(i, this[i])) break
            }
        """.trimIndent()
            println(jsCode)
            source.eval(jsCode)
        }
    }


    fun filter(block: (currentItem: Any?, index: Int, arr: JsArray)->Boolean): JsArray {
        val range = (0 until length)
        val result = JsArray(source.eval("[]") as JSObject)
        for (i in range) {
            val item = get(i)
            if (block(item, i, this))
                result.push(item)
        }
        return result
    }

    fun jsFilter(callback: JsArrayFilterCallback): JsArray {
        val result = inject(source, "filter", callback) { source, method->
            source.eval("this.filter((item, index, arr)=>{ return ${method}(item, index, arr)})")
        }
        return if (result is JSObject)
            JsArray(result)
        else
            throw  JsFunctionInvokeException("failed to invoke function 'filter()' on a js Array object")
    }

    fun map(callback: JsArrayMapCallback): JsArray {
        val result =  inject(source, "map", callback) { source, method->
            source.eval("this.map((item, index, arr)=>{ return ${method}(item, index, arr)})")
        }
        return if (result is JSObject)
            JsArray(result)
        else
            throw  JsFunctionInvokeException("failed to invoke function 'map()' on a js Array object")
    }

    fun every(callback: JsArrayEveryCallback): Boolean {
        val result = inject(source, "every", callback) { source, method->
            source.eval("this.every((item, index, arr)=>{ return ${method}(item, index, arr)})")
        }
        return if (result is Boolean)
            result
        else
            throw  JsFunctionInvokeException("failed to invoke function 'every()' on a js Array object")
    }

    fun some(callback: JsArrayEveryCallback): Boolean {
        val result = inject(source, "some", callback) { source, method->
            source.eval("this.some((item, index, arr)=>{ return ${method}(item, index, arr)})")
        }
        return if (result is Boolean)
            result
        else
            throw  JsFunctionInvokeException("failed to invoke function 'some()' on a js Array object")
    }

    fun reduce(callback: JsArrayReduceCallback): Any {
        source.setMember("__reduce", callback)
        val result = source.eval("this.reduce((total,current, index, arr)=>{return this.__reduce.call(total, current, index, arr)})")
        source.removeMember("__reduce")
        return result
    }

    fun reduceRight(callback: JsArrayReduceCallback): Any {
        source.setMember("__reduceRight", callback)
        val result = source.eval("this.reduceRight((total,current, index, arr)=>{return this.__reduceRight.call(total, current, index, arr)})")
        source.removeMember("__reduceRight")
        return result
    }

    override fun toString(): String {
        return source.call("toString") as String
    }

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false
        if (other !is JsArray)
            return false
        return this.source == other.source
    }

    override fun hashCode(): Int {
        return source.hashCode()
    }


    interface JsArrayForEachCallback {
        fun call(index: Int, item: Any?)
    }

    interface JsArrayFilterCallback {
        fun call(currentItem: Any?, index: Int, arr: JSObject): Boolean
    }

    interface JsArrayReduceCallback {
        fun call(total: Any?, currentValue: Any?, index: Int, arr: Any?): Any?
    }

    interface JsArrayFindCallback {
        fun call(item: Any?, index: Int, arr: Any?): Boolean
    }

    interface JsArrayMapCallback {
        fun call(item: Any?, index: Int, arr: Any?): Any?
    }

    interface JsArrayEveryCallback {
        fun call(item: Any?, index: Int, arr: Any?): Boolean
    }

    interface JsArraySomeCallback {
        fun call(item: Any?, index: Int, arr: Any?): Boolean
    }

    interface JsArrayForCallback {
        fun call(index: Int, item: Any?): Boolean
    }
}



object Undefined {
    override fun toString(): String {
        return "undefined"
    }
}

class JsValueTypeException(msg: String="value type is not as expected"): RuntimeException(msg)
class NotAnJsArrayException(msg: String="this js object is not a js Array") : RuntimeException(msg)
class JsFunctionInvokeException(msg: String): RuntimeException(msg)