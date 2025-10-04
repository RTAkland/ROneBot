import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 3:25 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

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

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
            add("-Xcontext-parameters")
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":ronebot-onebot-v11"))
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