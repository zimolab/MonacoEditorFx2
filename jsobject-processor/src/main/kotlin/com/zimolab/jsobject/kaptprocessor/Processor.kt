package com.zimolab.jsobject.kaptprocessor

import com.google.auto.service.AutoService
import com.zimolab.jsobject.annotations.JsInterface
import com.zimolab.jsobject.kaptprocessor.JsObjectProcessor.Companion.KAPT_KOTLIN_GENERATED_OPTION_NAME
import com.zimolab.jsobject.processors.AnnotationProcessingError
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedOptions(KAPT_KOTLIN_GENERATED_OPTION_NAME)
class JsObjectProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    lateinit var elementUtils: Elements
    lateinit var logger: Messager
    lateinit var filer: Filer
    lateinit var typeUtils: Types
    lateinit var options: Map<String, String>


    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        elementUtils = processingEnv.elementUtils
        logger = processingEnv.messager
        filer = processingEnv.filer
        typeUtils = processingEnv.typeUtils
        options = processingEnv.options

        InterfaceProcessor.initialize(elementUtils, typeUtils, logger)
        AbstractClassProcessor.initialize(elementUtils, typeUtils, logger)
        ClassFileGenerator.initialize(elementUtils, typeUtils, logger)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(JsInterface::class.qualifiedName.toString())
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val outputPath = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
            logger.printMessage(Diagnostic.Kind.ERROR,"Can't find the target directory for generated Kotlin files.")
            return false
        }

        val outputDirectory = File(outputPath).also { it.mkdirs() }

        if (annotations.isEmpty()) {
            return false
        }
        val allElements = roundEnv.getElementsAnnotatedWith(JsInterface::class.java)

        allElements.forEach { element ->
            if (!verifyElement(element)) {
                logger.printMessage(
                    Diagnostic.Kind.ERROR,
                    "@${JsInterface::class.java.simpleName} can only be applied on interface or abstract class",
                    element
                )
                throw AnnotationProcessingError("@${JsInterface::class.java.simpleName} can only be applied on interface or abstract class")
            }
            val resolvedJsInterface = processElement(element as TypeElement)
            resolvedJsInterface?.let {
                logger.printMessage(Diagnostic.Kind.NOTE, "generating file at ${outputDirectory.absoluteFile}", resolvedJsInterface.source)
                ClassFileGenerator.generate(outputDirectory, it)
            }
        }
        return true
    }

    fun verifyElement(element: Element): Boolean {
        if (element is TypeElement) {
            return element.kind == ElementKind.INTERFACE || (element.kind == ElementKind.CLASS && element.modifiers.contains(
                Modifier.ABSTRACT
            ))
        }
        return false
    }

    fun processElement(element: TypeElement): ResolvedJsInterface? {
            return when (element.kind) {
                ElementKind.INTERFACE -> {
                    InterfaceProcessor.process(element)
                }
                ElementKind.CLASS -> {
                    AbstractClassProcessor.process(element)
                }
                else -> {
                    null
                }
            }
    }
}

