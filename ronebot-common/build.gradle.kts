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
            freeCompilerArgs.apply {
                add("-Xjvm-default=all")
            }
        }
    }
    mingwX64()
    linuxX64()
    linuxArm64()
    macosArm64()
    macosX64()
    js(IR) { nodejs() }

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
            add("-Xcontext-parameters")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
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
                api(libs.ktor.client.core)
            }
        }
    }
}

suspendTransformPlugin {
    enabled = true
    includeRuntime = true
    transformers {
        useJvmDefault()
    }
    runtimeDependency {
        configurationName = "api"
    }
}