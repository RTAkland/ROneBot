import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    sourceSets {
       jvmMain {
            dependencies {
                api(project(":ronebot-common"))
                api(libs.okhttp)
            }
        }
    }
}