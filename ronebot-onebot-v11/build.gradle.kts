@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    mingwX64()
    linuxX64()
    macosX64()
    macosArm64()

    compilerOptions {
        freeCompilerArgs = listOf("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":ronebot-common"))
            }
        }

        commonTest {
            dependencies {
                implementation(project(":ronebot-onebot-v11"))
                implementation(project(":ronebot-permission"))
                implementation(kotlin("test"))
            }
        }

        jvmMain {
            dependencies {
                api(libs.java.websocket)
            }
        }
    }
}