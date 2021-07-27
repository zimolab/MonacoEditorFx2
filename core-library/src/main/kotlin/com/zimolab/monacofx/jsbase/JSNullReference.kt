package com.zimolab.monacofx.jsbase

import java.lang.RuntimeException

object JSNullReference: JsVariableReference {
    override fun jsClassName(): String {
        throw RuntimeException("This method should not be called in this implementation of JsVariableReference")
    }
    override fun jsVariableName(): String{
        throw RuntimeException("This method should not be called in this implementation of JsVariableReference")
    }
    override fun reference(): String = "null"

    override fun delete(): Boolean {
        throw RuntimeException("This method should not be called in this implementation of JsVariableReference")    }
}