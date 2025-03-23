import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("application")
    alias(libs.plugins.shadow)
    alias(libs.plugins.ktor)
}

repositories {
    maven("https://repo.maven.rtast.cn/releases/")
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

kotlin {
    explicitApi()

    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":ronebot-common-http"))
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.cors)
                implementation(libs.rtast.util.string)
            }
        }
    }
}