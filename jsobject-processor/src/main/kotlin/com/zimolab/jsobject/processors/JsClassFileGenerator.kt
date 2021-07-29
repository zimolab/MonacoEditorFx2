package com.zimolab.jsobject.processors

import com.google.common.util.concurrent.*
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.squareup.kotlinpoet.*
import com.zimolab.jsobject.asTypeName
import com.zimolab.jsobject.qualifiedName
import com.zimolab.jsobject.simpleNameStr
import javafx.scene.web.WebEngine
import netscape.javascript.JSObject
import java.io.OutputStream
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.reflect.KFunction

object JsClassFileGenerator : FutureCallback<Unit> {

    val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    val DEFAULT_THREAD_POOL_SIZE = CPU_COUNT * 2

    private lateinit var codeGenerator: CodeGenerator
    private lateinit var logger: KSPLogger
    private lateinit var options: Map<String, String>
    private var poolSize: Int = DEFAULT_THREAD_POOL_SIZE
    private var initialized: Boolean = false
    private val service: ListeningExecutorService by lazy {
        MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(poolSize))
    }
    private var callback: ((success: Boolean, exception: Throwable?) -> Unit)? = null

    class FileGeneratorTask(
        private val annotatedJsInterface: AnnotatedJsInterface,
        private val codeGenerator: CodeGenerator,
        private val logger: KSPLogger,
        private val options: Map<String, String>
    ) : Callable<Unit> {

        companion object {
            const val CLASS_NOTES = "This class is auto-generated from %S." +
                    "It may be overwritten at any time, every change to it will be lost. DO NOT MODIFY IT. " +
                    "Just inherit from it with your own implementation."

            const val PROP_NAME_TARGET_OBJECT = "targetObject"
            const val PROP_NAME_WEBENGINE = "webEngine"
            const val FUNC_NAME_MEMBER_EXIST = "exists"
            const val FUNC_ARG_MEMBER_NAME = "name"
            const val FUNC_ARG_VARARGS = "args"
            const val FUNC_ARG_JSCODE = "jsCode"
            const val SETTER_ARG_VALUE = "value"
            const val SETTER_BACK_FIELD = "field"
            const val STATIC_FUNC_NAME_NEW_INSTANCE = "new"

            const val CODE_FIELD_SETTER = "${PROP_NAME_TARGET_OBJECT}.setMember(%S,·${SETTER_ARG_VALUE})"

            const val CODE_FIELD_GETTER = "" +
                    "val·result·=·${PROP_NAME_TARGET_OBJECT}.getMember(%S)\n" +
                    "if(result·==·\"undefined\"·||·result·==·null)\n" +
                    "····throw·RuntimeException(\"'%L'·is·not·exists·in·underlying·js·object\")\n" +
                    "return·result·as·%T\n"

            const val CODE_NULLABLE_FILED_GETTER = "" +
                    "var·result·=·${PROP_NAME_TARGET_OBJECT}.getMember(%S)\n" +
                    "if(result·==·\"undefined\")\n" +
                    "····result = null\n" +
                    "return·result?.let·{·it·as·%T·}\n"

            const val CODE_NEW_INSTANCE = "" +
                    "val·clz·=·T::class\n" +
                    "if·(clz.isAbstract)\n" +
                    "····throw·InstantiationError(\"abstract·class·can·not·be·instantiated\")\n" +
                    "var·c:%T<*>?·=·null\n" +
                    "clz.constructors.forEach·{\n" +
                    "····if·(it.parameters.size·==·(${FUNC_ARG_VARARGS}.size·+·2))\n" +
                    "········c·=·it\n" +
                    "}\n" +
                    "if(c·==·null)\n" +
                    "····throw·InstantiationError(\"constructor·parameters·not·match\")\n" +
                    "val·targetObject·=·${PROP_NAME_WEBENGINE}.executeScript(${FUNC_ARG_JSCODE})\n" +
                    "if(targetObject·==·\"undefined\"·||·targetObject·!is·JSObject)\n" +
                    "····return null\n" +
                    "return·c?.call(targetObject·as·JSObject,·webEngine,·*${FUNC_ARG_VARARGS})·as?·T\n"

            const val CODE_MEMBER_EXIST = "return·${PROP_NAME_TARGET_OBJECT}.getMember(${FUNC_ARG_MEMBER_NAME})·!=·\"undefined\"\n"

            const val CODE_CALL_MEMBER_EXCEPTIONAL = "" +
                    "val·result·=·${PROP_NAME_TARGET_OBJECT}.call(%S,·%L)\n" +
                    "if(result·==·\"undefined\"·||·result·!is·%T)\n" +
                    "····throw·RuntimeException(\"return·value·type·is·not·as·expected\")\n" +
                    "return·result·#as·%T\n"

            const val CODE_CALL_MEMBER = "" +
                    "val·result·=·${PROP_NAME_TARGET_OBJECT}.call(%S,·%L)\n" +
                    "if(result·==·\"undefined\"·||·result·!is·%T)\n" +
                    "····return·null\n" +
                    "return·result·#as·%T\n"
        }

        // 在此处完成输出文件的生成和写出工作
        override fun call(): Unit {
            logger.info(
                "start to generate class file for class '${annotatedJsInterface.generatedClassName}' " +
                        "from '${annotatedJsInterface.interfaceDeclaration.simpleNameStr}' interface"
            )
            val dependencies = if (annotatedJsInterface.containingFile == null) {
                Dependencies(aggregating = true)
            } else {
                Dependencies(aggregating = true, annotatedJsInterface.containingFile!!)
            }
            val outputStream: OutputStream = codeGenerator.createNewFile(
                fileName = annotatedJsInterface.outputFilename,
                packageName = annotatedJsInterface.packageName,
                dependencies = dependencies
            )
            writeFile(createFile(createClass()), outputStream)
        }

        fun createClass(): TypeSpec {
            val generatedClassName = annotatedJsInterface.generatedClassName
            val packageName = annotatedJsInterface.packageName
            val superInterface = annotatedJsInterface.ksType.asTypeName()
            val companionObjectBuilder = TypeSpec.companionObjectBuilder()

            val className = ClassName(packageName, generatedClassName)
            // 类构造器
            val classBuilder = TypeSpec
                .classBuilder(className) // 类名
                .addModifiers(KModifier.ABSTRACT) // 设置为抽象类
                .addSuperinterface(superInterface) // 实现被注解的接口
            // 添加注释
            addNotes(classBuilder)
            // 创建构造函数
            createConstructor(classBuilder)
            // 创建静态类成员
            createStaticMembers(companionObjectBuilder)
            // 创建实例成员函数
            createInstanceFunctions(classBuilder)
            // 添加字段
            addJsFields(classBuilder, annotatedJsInterface.allFields)
            // 添加js函数
            addJsFunctions(classBuilder, annotatedJsInterface.allJsFunctions)

            classBuilder.addType(companionObjectBuilder.build())

            return classBuilder.build()
        }

        fun createFile(clazz: TypeSpec): FileSpec {
            val file = FileSpec.builder(annotatedJsInterface.packageName, annotatedJsInterface.outputFilename)
            file.addType(clazz)
            return file.build()
        }

        fun writeFile(file: FileSpec, outputStream: OutputStream) {
            outputStream.bufferedWriter().use {
                try {
                    file.writeTo(it)
                } catch (e: Throwable) {
                    logger.error(e.message?:"io error", null)
                } finally {
                    it.flush()
                    it.close()
                }
            }
        }

        private fun addNotes(classBuilder: TypeSpec.Builder) {
            classBuilder.addKdoc(CLASS_NOTES, annotatedJsInterface.interfaceName)
            classBuilder.addKdoc("\n@${LocalDateTime.now()}\n")
        }

        private fun createConstructor(classBuilder: TypeSpec.Builder) {
            val pConstructor = FunSpec.constructorBuilder()
                .addParameter(PROP_NAME_TARGET_OBJECT, JSObject::class)
                .addParameter(PROP_NAME_WEBENGINE, WebEngine::class)
                .build()

            classBuilder.primaryConstructor(pConstructor)
                .addProperty(
                    PropertySpec.builder(PROP_NAME_TARGET_OBJECT, JSObject::class)
                        .initializer(PROP_NAME_TARGET_OBJECT)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(PROP_NAME_WEBENGINE, WebEngine::class)
                        .initializer(PROP_NAME_WEBENGINE)
                        .build()
                )
        }

        private fun createStaticMembers(companionObjectBuilder: TypeSpec.Builder) {
            // new函数
            val functionNew = FunSpec.builder(STATIC_FUNC_NAME_NEW_INSTANCE)
                .addModifiers(KModifier.INLINE) // inline fun new
                .addTypeVariable(
                    TypeVariableName(
                        "T",
                        bounds = arrayOf(annotatedJsInterface.ksType.asTypeName())
                    ).copy(reified = true)
                ) // <reified T:annotatedClass.typeElement>
                .returns(TypeVariableName("T").copy(nullable = true)) // : T?
                .addParameter(PROP_NAME_WEBENGINE, WebEngine::class)
                .addParameter(FUNC_ARG_JSCODE, String::class)
                .addParameter(FUNC_ARG_VARARGS, Any::class, KModifier.VARARG) // vararg args: Any
                .addCode(CODE_NEW_INSTANCE, KFunction::class)
                .build()
            companionObjectBuilder.addFunction(functionNew)
        }

        private fun createInstanceFunctions(classBuilder: TypeSpec.Builder) {
            val functionExists = FunSpec.builder(FUNC_NAME_MEMBER_EXIST)
                .addModifiers(KModifier.PUBLIC, KModifier.OPEN)
                .addParameter(FUNC_ARG_MEMBER_NAME, String::class)
                .returns(Boolean::class)
                .addCode(CODE_MEMBER_EXIST)
                .build()
            classBuilder.addFunction(functionExists)
        }

        private fun addJsFields(classBuilder: TypeSpec.Builder, allFields: MutableList<ResolvedJsField>) {
            allFields.forEach { field ->
                val fieldName = field.fieldName
                val memberName = field.memberName
                val fieldType = field.ksType.asTypeName()
                val mutable = field.mutable
                val ignoreUndefined = field.ignoreUndefined


                val builder = PropertySpec.builder(fieldName, fieldType)
                    .mutable(mutable)
                    .addModifiers(KModifier.OVERRIDE)


                if (mutable) {
                    // 对于var属性需要生成相应的setter
                    val code = CODE_FIELD_SETTER
                    val setter = FunSpec.setterBuilder()
                        .addParameter(SETTER_ARG_VALUE, fieldType)
                        .addCode(code, memberName)
                        .build()
                    builder.setter(setter)
                }

                // 无论是var还是val，都需要生成相应的getter
                val getter = if (ignoreUndefined && fieldType.isNullable) {
                    val code = CODE_NULLABLE_FILED_GETTER
                    FunSpec.getterBuilder()
                        .addCode(code, memberName, fieldType.copy(nullable = false))
                        .build()
                } else {
                    val code = CODE_FIELD_GETTER
                    FunSpec.getterBuilder()
                        .addCode(code, memberName, fieldName,fieldType.copy(nullable = false))
                        .build()
                }
                builder.getter(getter)
                classBuilder.addProperty(builder.build())
            }
        }

        private fun addJsFunctions(classBuilder: TypeSpec.Builder, allJsFunctions: MutableSet<ResolvedJsFunction>) {
            allJsFunctions.forEach { func ->
                val funcName = func.functionName
                val memberName = func.memberName
                val funcParams = func.parameters
                val funcReturnType = func.returnType.asTypeName()
                val funcRaiseException = func.exceptionOnUndefined && !funcReturnType.isNullable

                val funcBuilder = FunSpec.builder(funcName)
                    .addModifiers(KModifier.OVERRIDE)
                    .returns(returnType = funcReturnType)

                // 添加参数
                funcParams.forEach { p ->
                    val param = if (p.isVararg) {
                        ParameterSpec.builder(p.name, p.type.asTypeName(), KModifier.VARARG).build()
                    } else {
                        ParameterSpec.builder(p.name, p.type.asTypeName()).build()
                    }
                    funcBuilder.addParameter(param)
                }
                val buffer = StringBuilder()
                funcParams.joinTo(buffer, ",") {
                    if (it.isVararg) "*${it.name}" else it.name
                }

                var code = if (funcRaiseException) CODE_CALL_MEMBER_EXCEPTIONAL else CODE_CALL_MEMBER

                code = if (funcReturnType.isNullable) {
                    code.replace("#as", "as?")
                } else {
                    code.replace("#as", "as")
                }

                funcBuilder.addCode(
                    code,
                    memberName,
                    buffer.toString(),
                    funcReturnType.copy(nullable = false),
                    funcReturnType.copy(nullable = false)
                )

                classBuilder.addFunction(funcBuilder.build())
            }
        }
    }

    fun initialize(
        codeGenerator: CodeGenerator,
        logger: KSPLogger,
        options: Map<String, String>,
        poolSize: Int = DEFAULT_THREAD_POOL_SIZE
    ) {
        if (initialized) {
            logger.warn("JsClassFileGenerator already initialized")
            return
        }
        this.codeGenerator = codeGenerator
        this.logger = logger
        this.options = options
        if (poolSize <= 0)
            throw RuntimeException("pool size cannot be 0 or negative")
        if (poolSize < CPU_COUNT) {
            this.poolSize = poolSize
            logger.warn("thread pool size may be to low")

        }
        initialized = true
    }

    fun submit(annotatedJsInterface: AnnotatedJsInterface): ListenableFuture<Unit>? {
        if (!initialized)
            throw RuntimeException("JsClassFileGenerator is not initialized")

        FileGeneratorTask(
            annotatedJsInterface,
            codeGenerator,
            logger,
            options
        ).call()
        return null
    }

    fun cancel(future: ListenableFuture<Unit>) {
        future.cancel(false)
    }

    fun onTaskDone(callback: (success: Boolean, exception: Throwable?) -> Unit) {
        this.callback = callback
    }

    fun shutdown() {
        if (initialized)
            service.shutdown()
    }

    fun shutdownNow() {
        if (initialized)
            service.shutdownNow()
    }

    override fun onSuccess(result: Unit?) {
        callback?.invoke(true, null)
    }

    override fun onFailure(t: Throwable) {
        callback?.invoke(false, t)
    }
}