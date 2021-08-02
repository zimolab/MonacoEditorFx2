plugins {
    application
    kotlin("jvm")
}

group = "com.zimolab.monacofx"
version = "1.0-SNAPSHOT"
val tornadofx_version: String by project

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation(project(":core-library"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.5.1")
    implementation("no.tornado:tornadofx-controlsfx:0.1")
    implementation("com.alibaba:fastjson:1.2.76")
    implementation("com.sun.webkit:webview-deps:1.3.2")
    implementation ("no.tornado:tornadofx:$tornadofx_version")
    implementation(project(":jsobject-processor"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}