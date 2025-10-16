/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:52 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.suspend.transformer)
    alias(libs.plugins.interop.shield)
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

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
            add("-Xcontext-parameters")
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":ronebot-common"))
            api(libs.ktor.client.core)
            api(libs.ktor.client.websockets)
            api(libs.arrow.kt)
            api(libs.ktor.client.cio)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
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