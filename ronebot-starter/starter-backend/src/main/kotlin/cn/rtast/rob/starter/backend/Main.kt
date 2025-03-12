/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend

import cn.rtast.rob.common.ext.Http
import cn.rtast.rob.starter.backend.entity.LatestVersion
import cn.rtast.rob.starter.backend.util.Resources
import cn.rtast.rob.starter.backend.util.zipDirectory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import java.io.File
import java.util.*

private const val VERSION_URL =
    "https://repo.maven.rtast.cn/api/maven/latest/version/releases/cn/rtast/ronebot-onebot-v11"

private val tempDir = File("./tmp").apply { mkdirs() }

public fun main() {
    embeddedServer(Netty, 9099) {
        install(CORS) {
            anyHost()
        }
        routing {
            get("/") {
                call.respondText("200")
            }

            get("/api/latest/version") {
                val response = Http.get<LatestVersion>(VERSION_URL)
                call.respondText(response.version)
            }

            post("/api/generate") {
                val id = UUID.randomUUID().toString()
                val form = call.receiveParameters()
                val desForm = form.toMap()
                val projectName = desForm["projectName"]?.first() ?: return@post
                val groupId = desForm["group"]?.first() ?: return@post
                val robVersion = desForm["robVersion"]?.first() ?: return@post
                val gradleVersion = desForm["gradleVersion"]?.first() ?: return@post
                val kotlinVersion = desForm["kotlinVersion"]?.first() ?: return@post
                val packageName = "${groupId}.${projectName.lowercase()}"
                val wrapperProp = String(Resources.load("gradle/gradle-wrapper.properties"))
                    .replace("{{GRADLE_VERSION}}", gradleVersion)
                val buildGradleKts = String(Resources.load("build.gradle.kts"))
                    .replace("{{GROUP_ID}}", groupId)
                    .replace("{{KOTLIN_VERSION}}", kotlinVersion)
                    .replace("{{ROB_VERSION}}", robVersion)
                    .replace("{{MAIN_CLASS}}", "$packageName.MakeKt")
                val mainKt = String(Resources.load("Main.kt"))
                    .replace("{{APP_PACKAGE}}", packageName)
                val settingsGradleKts = String(Resources.load("settings.gradle.kts"))
                    .replace("{{PROJECT_NAME}}", projectName)
                val tempGeneratedDir = File(tempDir, id).apply { mkdirs() }
                File(tempGeneratedDir, "gradlew").writeBytes(Resources.load("gradlew"))
                File(tempGeneratedDir, "gradlew.bat").writeBytes(Resources.load("gradlew.bat"))
                File(tempGeneratedDir, ".gitignore").writeBytes(Resources.load("gitignore"))
                File(tempGeneratedDir, "gradle.properties").writeBytes(Resources.load("gradle.properties"))
                File(tempGeneratedDir, "build.gradle.kts").writeText(buildGradleKts)
                File(tempGeneratedDir, "settings.gradle.kts").writeText(settingsGradleKts)
                val gradleDir = File(tempGeneratedDir, "gradle").apply { mkdirs() }
                File(gradleDir, "gradle-wrapper.jar").writeBytes(Resources.load("gradle/gradle-wrapper.jar.1"))
                File(gradleDir, "gradle-wrapper.properties").writeText(wrapperProp)
                val srcDir = File(tempGeneratedDir, "src/main/kotlin/${packageName.replace(".", "/")}")
                    .apply { mkdirs() }
                File(srcDir, "Main.kt").writeText(mainKt)
                val generatedZipFile = File(tempGeneratedDir, "example-ronebot-onebot-v11.zip")
                zipDirectory(tempGeneratedDir, generatedZipFile)
                val targetBytes = generatedZipFile.readBytes()
                tempGeneratedDir.deleteRecursively()
                call.respondBytes(targetBytes, contentType = ContentType.parse("application/zip"))
            }
        }
    }.start(true)
}