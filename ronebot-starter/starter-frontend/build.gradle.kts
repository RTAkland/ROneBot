@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    explicitApi()
    wasmJs {
        outputModuleName = "ROneBot-Starter-Frontend"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "ronebot-starter-frontend.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.core)
            implementation(project(":ronebot-starter:starter-common"))
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "cn.rtast.rob.starter.frontend.resources"
    generateResClass = auto
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}

val downloadCustomFont by tasks.registering {
    val fontFile = File(project.layout.projectDirectory.dir("src/wasmJsMain/resources/assets").asFile, "yh.ttf")
    if (!fontFile.exists()) {
        println("字体文件缺失, 正在下载中...")
        fontFile.writeBytes(URI("https://r2-static.rtast.cn/yh.ttf").toURL().readBytes())
    }
}

tasks.all {
    if (this.name != "downloadCustomFont") {
        this.dependsOn(downloadCustomFont)
    }
}