import cn.rtast.jvmonly.linter.JvmOnlyReportLevel

plugins {
    id("cn.rtast.jvmonly-linter") version "{{JVM_ONLY_LINTER_VERSION}}"
    kotlin("multiplatform") version "{{KOTLIN_VERSION}}"
    {{SHADOW_JAR}}
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    // 补药移除这里的仓库否则会导致无法下载依赖
    maven("https://repo.maven.rtast.cn/releases")
    // maven("https://repo.maven.rtast.cn/snapshots")
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

jvmOnly {
    enabled = true
    reportLevel = JvmOnlyReportLevel.ERROR
}

{{SHADOW_JAR_CONFIG}}