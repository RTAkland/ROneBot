plugins {
    kotlin("multiplatform") version "{{KOTLIN_VERSION}}"
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases/")
}

kotlin {
    {{LINUX}}
    {{LINUX_ARM}}
    {{MINGW}}
    {{MACOS}}
    {{MACOS_ARM}}
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation("cn.rtast.rob:{{PLATFORM}}:{{ROB_VERSION}}"){{EXTRA_FEATURES}}
            }
        }
    }
}