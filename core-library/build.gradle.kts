plugins {
    java
    `java-library`
    id("com.google.devtools.ksp")

    kotlin("jvm")
}

group = "com.zimolab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation(project(":jsobject-processor"))
    ksp(project(":jsobject-processor"))
}

sourceSets.main {
    java.srcDir("build/generated/ksp/main/kotlin/")
    java.srcDir("build/generated/ksp/main/java/")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}