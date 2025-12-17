import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 16:13
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }

    explicitApi()

    sourceSets {
        commonMain.dependencies {
            api(project(":ronebot-serverless:core"))
        }
    }
}