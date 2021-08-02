package com.zimolab.jsobject

import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.io.OutputStream
import kotlin.reflect.KClass

fun KClass<*>.asKSType(resolver: Resolver) =
    resolver
        .getClassDeclarationByName(resolver.getKSNameFromString(this.qualifiedName!!))
        ?.asType(emptyList())


infix fun KSClassDeclaration.subclassOf(superclass: KClass<out Any>): Boolean {
    return this.getAllSuperTypes().any { it.declaration.qualifiedName?.asString() == superclass.qualifiedName }
}

fun KSAnnotated.findAnnotations(annotationType: KClass<out Annotation>) =
    this.annotations.filter {
        it.shortName.asString() == annotationType.simpleName
                && it.annotationType.resolve().declaration.qualifiedName?.asString() == annotationType.qualifiedName
    }

fun KSAnnotated.hasAnnotation(annotationType: KClass<out Annotation>): Boolean =
    this.findAnnotations(annotationType).count() > 0

inline fun <reified T> KSAnnotation.findArgument(name: String, defaultValue: T): T {
    if (this.arguments.isEmpty())
        return defaultValue
    val r = this.arguments.find { it.name?.asString() == name}
    return if (r?.value == null)
        defaultValue
    else
        r.value as T
}

fun KSType.isAssignableTo(type: KSType): Boolean = type.isAssignableFrom(this)

internal fun KSDeclaration.getLocalQualifiedName(): List<String> =
    if (this.parentDeclaration != null)
        this.parentDeclaration!!.getLocalQualifiedName() + this.simpleName.asString()
    else listOf(this.simpleName.asString())

internal fun KSClassDeclaration.asClassName(): ClassName =
    ClassName(this.packageName.asString(), this.getLocalQualifiedName())


internal fun KSTypeParameter.asTypeVariableName(): TypeVariableName {
    return TypeVariableName.invoke(
        name = name.asString(),
        bounds = this.bounds.map { it.resolve().asTypeName() }.toMutableList().toTypedArray(),
        variance = when (variance) {
            Variance.INVARIANT, Variance.STAR -> null
            Variance.CONTRAVARIANT -> KModifier.IN
            Variance.COVARIANT -> KModifier.OUT
        }
    )
}

internal fun KSType.asTypeName(): TypeName {
    return if (this.declaration is KSClassDeclaration) {
        val declarationName = (this.declaration as KSClassDeclaration).asClassName()
        val candidate = if (this.arguments.isNotEmpty()) {
            declarationName.parameterizedBy(*this.arguments.map { it.type!!.resolve().asTypeName() }.toTypedArray())
        } else declarationName

        if (this.isMarkedNullable) candidate.copy(nullable = true) else candidate
    } else if (this.declaration is KSTypeParameter) {
        val declarationName: TypeVariableName = (this.declaration as KSTypeParameter).asTypeVariableName()
        if (this.isMarkedNullable) declarationName.copy(nullable = true) else declarationName
    } else {
        throw RuntimeException("Failed to create TypeName for $this")
    }
}

internal val KSTypeReference.referenceName: String
    get() {
        return "${this.resolve().declaration.qualifiedName!!.getQualifier()}.${this.element}"
    }

internal val KSTypeReference.isCollectionInterface: Boolean
    get() {
        val name = this.referenceName
        val collectionInterfaces = setOf(
            "kotlin.Array",
            "kotlin.collections.List",
            "kotlin.collections.MutableList",
            "kotlin.collections.Set",
            "kotlin.collections.MutableSet",
            "kotlin.collections.Map",
            "kotlin.collections.MutableMap"
        )

        return name in collectionInterfaces
    }

internal val KSTypeReference.isPrimitiveArray: Boolean
    get() {
        val name = this.referenceName
        val primitiveArrays = setOf(
            "kotlin.UIntArray",
            "kotlin.UShortArray",
            "kotlin.UByteArray",
            "kotlin.ULongArray",
            "kotlin.BooleanArray",
            "kotlin.IntArray",
            "kotlin.ShortArray",
            "kotlin.ByteArray",
            "kotlin.LongArray",
            "kotlin.DoubleArray",
            "kotlin.FloatArray",
            "kotlin.CharArray"
        )

        return name in primitiveArrays
    }


internal val KSTypeReference.isPrimitive: Boolean
    get() {
        val name = this.referenceName
        val primitiveArrays = setOf(
            "kotlin.Int",
            "kotlin.Short",
            "kotlin.Byte",
            "kotlin.Long",
            "kotlin.Boolean",
            "kotlin.UInt",
            "kotlin.UShort",
            "kotlin.UByte",
            "kotlin.ULong",
            "kotlin.Double",
            "kotlin.Float",
            "kotlin.Char"
        )

        return name in primitiveArrays
    }

internal val KSType.qualifiedName: String
    get() {
        return if (this.generic)
            this.toString()
        else
            "${this.declaration.qualifiedName!!.getQualifier()}.$this"
    }

internal val KSType.generic: Boolean
    get() = this.declaration is KSTypeParameter

internal val KSType.simpleName: String
    get() = declaration.simpleNameStr

internal val KSDeclaration.simpleNameStr: String
    get() = simpleName.asString()

internal val KSDeclaration.qualifiedNameStr: String
    get() = checkNotNull(qualifiedName).asString()

internal val KSFunctionDeclaration.simpleNameStr: String
    get() = simpleName.asString()

internal val KSFunctionDeclaration.qualifiedNameStr: String
    get() = checkNotNull(qualifiedName).asString()

internal val KSClassDeclaration.isClass: Boolean
    get() = classKind in listOf(
        ClassKind.CLASS,
        ClassKind.ENUM_CLASS,
        ClassKind.OBJECT
    )

internal val KSDeclaration.packageNameStr: String
    get() = packageName.asString()

fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}
operator fun OutputStream.plusAssign(str: String) {
    this.write(str.toByteArray())
}

fun Sequence<KSAnnotation>.findAnnotations(annotationType: KClass<out Annotation>) =
    this.filter { it.annotationType.resolve().qualifiedName == annotationType.qualifiedName }
