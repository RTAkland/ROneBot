/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.starter.backend

import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.entity.LatestVersion
import cn.rtast.rob.starter.backend.generators.GeneratorProperty
import cn.rtast.rob.starter.backend.util.Http
import cn.rtast.rob.starter.backend.util.Resources
import cn.rtast.rob.starter.backend.util.mkdirs
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
                println(call.receive<GeneratorProperty>())
                val receivedForm = call.receiveParameters()
                    .toMap()

//                call.respondBytes(targetBytes, contentType = ContentType.parse("application/zip"))
            }
        }
    }.start(true)
}