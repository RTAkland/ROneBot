import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
            freeCompilerArgs.add("-Xbinary=preCodegenInlineThreshold=40")
        }
    }

    sourceSets {
        jvmMain  {
            dependencies {
                api(project(":ronebot-common-http"))
                api(libs.ktor.server.core)
                api(libs.ktor.server.netty)
                api(libs.bcprov)
                api(libs.bcpg)
                api(libs.gson)
            }
        }
    }
}