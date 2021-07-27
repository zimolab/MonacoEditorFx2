plugins {
    kotlin("jvm")
}

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.5.21"))
    }
}

group = "com.zimolab.jsobject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}