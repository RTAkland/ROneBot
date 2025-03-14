import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    application
    alias(libs.plugins.shadow)
    id("io.ktor.plugin") version "3.1.0"
}

repositories {
    maven("https://repo.maven.rtast.cn/releases/")
}

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    implementation(project(":ronebot-common-http"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors)
    implementation(libs.rtast.util.string)
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}

tasks.shadowJar {
    archiveFileName = "app.jar"
}

application {
    mainClass = "cn.rtast.rob.starter.backend.MainKt"
}