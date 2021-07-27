plugins {
    java
    kotlin("jvm")
}

group = "com.zimolab.jsobject"
version = "1.0-SNAPSHOT"
val kspVersion: String by project
val kspAutoServiceVersion: String by project

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    // 引入ksp库
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    // kotlin反射
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    // kotlin代码生成
    implementation("com.squareup:kotlinpoet:1.9.0")
    implementation("com.google.guava:guava:30.1.1-jre")

}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}