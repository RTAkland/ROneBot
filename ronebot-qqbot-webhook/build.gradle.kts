import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
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
                api(project(":ronebot-common-http"))
                api(libs.ktor.server.core)
                api(libs.ktor.server.cio)
            }
        }

        jvmMain {
            dependencies {
                api(libs.bcprov)
                api(libs.bcpg)
            }
        }

        nativeMain {
            dependencies {
                api(project(":ronebot-utils:ronebot-bytearray"))
                api(libs.curve25519.kotlin)
            }
        }
    }
}

suspendTransform {
    enabled = true
    includeRuntime = true
    useJvmDefault()
}