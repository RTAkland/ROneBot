plugins {
    kotlin("jvm") version "{{KOTLIN_VERSION}}"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "{{GROUP_ID}}"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases/")
}

dependencies {
    implementation("cn.rtast:ronebot-onebot-v11:{{ROB_VERSION}}")
}

tasks.build {
    dependsOn(tasks.shadowjar)
}