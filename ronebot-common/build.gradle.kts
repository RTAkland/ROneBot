@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    mingwX64()
    linuxX64()

    compilerOptions {
        freeCompilerArgs = listOf("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines)
                api(libs.kotlin.serialization)
                api(libs.kotlin.stdlib)
                api(libs.stately.concurrent.collections)
                api(libs.kotlinx.io)
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
                api(libs.ktor.server.websockets)
                api(libs.ktor.client.cio)
                api(libs.ktor.client.core)
                api(libs.ktor.client.websockets)
            }
        }
    }
}