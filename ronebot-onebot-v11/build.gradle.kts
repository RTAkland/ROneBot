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
    js(IR) {
        nodejs {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":ronebot-common"))
        }

        jvmMain.dependencies {
            api(libs.java.websocket)
            api(libs.brigadier)
            api("org.nanohttpd:nanohttpd:2.3.1")
        }

        nativeMain.dependencies {
            api(libs.ktor.server.websockets)
            api(libs.ktor.client.websockets)
        }

        appleMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            api(libs.ktor.client.curl)
        }

        mingwX64Main.dependencies {
            api(libs.ktor.client.winhttp)
        }

        jsMain.dependencies {
            api(project(":ronebot-common-http"))
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

suspendTransform {
    enabled = true
    includeRuntime = true
    useJvmDefault()
}