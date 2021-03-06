pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    val kaptVersion: String by settings
    plugins {
        id("com.google.devtools.ksp") version kspVersion
        kotlin("jvm") version kotlinVersion
        //kotlin("kapt") version kaptVersion
    }
    repositories {
        gradlePluginPortal()
        google()
    }
}


rootProject.name = "MonacoEditorFx2"
include("jsobject-processor")
include("core-library")
include("api-demo")
