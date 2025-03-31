plugins {
    kotlin("jvm") version "{{KOTLIN_VERSION}}"
    id("application")
    id("com.gradleup.shadow") version "8.3.0"
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation("cn.rtast.rob:{{PLATFORM}}:{{ROB_VERSION}}"){{EXTRA_FEATURES}}
}

application {
    mainClass = "{{MAIN_CLASS}}"
}

tasks.build {
    dependsOn(tasks.shadowJar)
}