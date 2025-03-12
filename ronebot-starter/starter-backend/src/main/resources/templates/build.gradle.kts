plugins {
    kotlin("jvm") version "{{KOTLIN_VERSION}}"
    id("com.gradleup.shadow") version "8.3.0"
    id("application")
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases/")
}

dependencies {
    implementation("cn.rtast:ronebot-onebot-v11:{{ROB_VERSION}}")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

application {
    mainClass = "{{MAIN_CLASS}}"
}