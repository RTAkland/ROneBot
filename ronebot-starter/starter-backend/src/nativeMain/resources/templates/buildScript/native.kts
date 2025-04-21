import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    kotlin("multiplatform") version "{{KOTLIN_VERSION}}"
    {{SHADOW_PLUGIN}}
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases")
    maven("https://repo.maven.rtast.cn/snapshots")
}

kotlin {
    {{LINUX}}
    {{LINUX_ARM}}
    {{MINGW}}
    {{MACOS}}
    {{MACOS_ARM}}
    {{JVM}}

    sourceSets {
        commonMain {
            dependencies {
                implementation("cn.rtast.rob:{{PLATFORM}}:{{ROB_VERSION}}"){{EXTRA_FEATURES}}
            }
        }
    }
}

{{SHADOW_CONFIG}}