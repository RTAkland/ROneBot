import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.suspend.transformer)
}

kotlin {
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
    js(IR) {
        nodejs()
    }

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":ronebot-common"))
            }
        }

        jvmMain {
            dependencies {
                api(libs.okhttp)
            }
        }

        nativeMain {
            dependencies {
                api(libs.ktor.client.core)
                api(libs.ktor.client.cio)
            }
        }

        jsMain.dependencies {
            api(libs.ktor.client.core)
            api(libs.ktor.client.cio)
            api(libs.ktor.server.core)
            api(libs.ktor.server.cio)
        }
    }
}

suspendTransform {
    enabled = true
    includeRuntime = true
    useJvmDefault()
}