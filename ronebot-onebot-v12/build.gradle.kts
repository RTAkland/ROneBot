/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:34
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.suspend.transformer)
}

repositories {
    maven("https://libraries.minecraft.net")
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
        commonMain.dependencies {
            api(project(":ronebot-common"))
            api(libs.ktor.server.websockets)
            api(libs.ktor.server.cio)
            api(libs.ktor.client.websockets)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.cio)
        }

        appleMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            api(libs.ktor.client.curl)
        }

        mingwMain.dependencies {
            api(libs.ktor.client.winhttp)
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