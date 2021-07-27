package com.zimolab.monacofx.util

import com.zimolab.monacofx.jsbase.JsBridge
import java.util.logging.*
import java.util.logging.Logger


public object Logger : JsBridge {
    private const val JS_NAME = "javaLogger"
    private const val DESCRIPTION =
    """Usage:
        Logger.info(msg: String);
        Logger.finer(msg: String);
        Logger.finest(msg: String);
        Logger.fine(msg: String);
        Logger.log(msg: String);
        Logger.warn(msg: String);
        Logger.error(msg: String);
    """
    val DEFAULT_LEVEL = Level.ALL
    val DEFAULT_HANDLER by lazy {
        val handler = ConsoleHandler()
        handler.level = DEFAULT_LEVEL
        handler.formatter = SimpleFormatter()
        handler
    }

    private val logger: Logger by lazy {
        Logger.getLogger(this.javaClass.canonicalName)
    }

    init {
        logger.level = DEFAULT_LEVEL
        logger.addHandler(DEFAULT_HANDLER)
    }

    fun setLevel(level: Level) {
        logger.level = level
    }

    fun addHandler(handler: Handler) {
        logger.addHandler(handler)
    }

    fun removeHandler(handler: Handler) {
        logger.removeHandler(handler)
    }

    override fun getJavascriptName() = JS_NAME
    override fun getDescription() = DESCRIPTION

    fun info(msg: String) = println(msg)
    fun finer(msg: String) = logger.finer(msg)
    fun finest(msg: String) = logger.finest(msg)
    fun fine(msg: String) = logger.fine(msg)
    fun log(msg: String) = logger.log(Level.ALL, msg)
    fun warn(msg: String) = logger.warning(msg)
    fun error(msg: String) = logger.severe(msg)
}
