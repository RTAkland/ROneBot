/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.starter.backend

import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.entity.LatestVersion
import cn.rtast.rob.starter.backend.enums.ProjectType
import cn.rtast.rob.starter.backend.util.Http
import cn.rtast.rob.starter.backend.util.Resources
import cn.rtast.rob.starter.backend.util.generateProject
import cn.rtast.rob.starter.backend.util.mkdirs
import cn.rtast.rob.starter.common.ExtraFeature
import cn.rtast.rob.starter.common.ROneBotTarget
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.io.files.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val VERSION_URL =
    "https://repo.maven.rtast.cn/api/maven/latest/version/releases/cn/rtast/ronebot-onebot-v11"

val tempDir: Path = Path("./tmp").mkdirs()
val config: Config = Resources.loadConfig()

fun main() {
    embeddedServer(CIO, port = 9099) {
        install(CORS) {
            anyHost()
        }

        routing {
            get("/") {
                call.respondText(
                    "200 Success! This is the ROneBot Starter backend, you may use the frontend: " +
                            "${config.frontend} to generate a template project. Have Fun!\n" +
                            "200! 这是ROneBot Starter 的后端, 这里对你来说可能没什么用, 你可能需要前往前端: ${config.frontend} " +
                            "来生成项目模板"
                )
            }

            get("/api/latest/version") {
                val response = Http.get<LatestVersion>(VERSION_URL)
                call.respondText(response.version)
            }

            post("/api/generate") {
                val id = Uuid.random().toString()
                val form = call.receiveParameters()
                val desForm = form.toMap()
                val projectType =
                    ProjectType.fromString(desForm["type"]?.first().toString()) ?: ProjectType.OneBot11
                val projectName = desForm["projectName"]?.first() ?: "ExampleROB"
                val groupId = desForm["group"]?.first() ?: "com.example.rob"
                val robVersion = desForm["robVersion"]?.first() ?: Http.get<LatestVersion>(VERSION_URL).version
                val kotlinVersion = desForm["kotlinVersion"]?.first() ?: "2.1.10"
                val gradleVersion = desForm["gradleVersion"]?.first() ?: "8.13"
                val extraFeatures =
                    desForm["features"]?.first()?.split(",")?.mapNotNull { ExtraFeature.fromList(it) }
                        ?: emptyList()
                val targets =
                    desForm["targets"]?.first()?.split(",")?.mapNotNull { ROneBotTarget.fromString(it) }
                        ?: emptyList()
                val targetBytes = generateProject(
                    projectType, groupId, projectName,
                    kotlinVersion, robVersion, id, gradleVersion,
                    extraFeatures, targets
                )
                call.respondBytes(targetBytes, contentType = ContentType.parse("application/zip"))
            }
        }
    }.start(true)
}