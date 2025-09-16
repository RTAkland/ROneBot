/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend

import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.entity.LatestVersion
import cn.rtast.rob.starter.backend.generators.impl.MilkyGenerator
import cn.rtast.rob.starter.backend.util.*
import cn.rtast.rob.starter.common.GeneratorProperty
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.io.files.Path

private const val VERSION_URL =
    "https://repo.maven.rtast.cn/api/maven/latest/version/releases/cn/rtast/ronebot-onebot-v11"

val tempDir: Path = Path("./tmp").mkdirs()
val config: Config = Resources.loadConfig()

fun main() {
    embeddedServer(CIO, port = 9099, host = "0.0.0.0") {
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
                val receivedData = call.receiveText().fromJson<GeneratorProperty>()
                val response = when (receivedData.protocol) {
                    "milky" -> MilkyGenerator(receivedData).generate()
                    else -> throw IllegalStateException("没有此类型的生成器: ${receivedData.protocol}")
                }
                call.respondText(response.toJson(), ContentType.Application.Json)
            }
        }
    }.start(true)
}