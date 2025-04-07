@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.suspend.transformer)
}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
    mingwX64()
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":ronebot-utils:ronebot-concurrency"))
                api(project(":ronebot-utils:ronebot-bytearray"))
                api(libs.kotlinx.coroutines)
                api(libs.kotlin.serialization)
                api(libs.kotlin.stdlib)
                api(libs.kotlinx.io)
                api(libs.klogging)
            }
        }

        jvmMain {
            dependencies {
                api(libs.java.websocket)
            }
        }

        nativeMain {
            dependencies {
                api(libs.ktor.server.core)
                api(libs.ktor.server.cio)
                api(libs.ktor.client.cio)
                api(libs.ktor.client.core)
            }
        }
    }
}

suspendTransform {
    enabled = true
    includeRuntime = true
    useJvmDefault()
}