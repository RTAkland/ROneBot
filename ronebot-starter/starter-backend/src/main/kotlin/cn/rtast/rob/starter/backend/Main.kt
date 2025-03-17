/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend

import cn.rtast.rob.common.http.Http
import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.entity.LatestVersion
import cn.rtast.rob.starter.backend.enums.ExtraFeature
import cn.rtast.rob.starter.backend.enums.ProjectType
import cn.rtast.rob.starter.backend.util.Resources
import cn.rtast.rob.starter.backend.util.generateProject
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

public val tempDir: File = File("./tmp").apply { mkdirs() }
public val config: Config = Resources.loadConfig()

public fun main() {
    embeddedServer(Netty, 9099) {
        install(CORS) {
            anyHost()
        }

        routing {
            get("/") {
                call.respondText(
                    "200 Success! This is the ROneBot Starter backend, you may use the frontend: " +
                            "${config.frontend} to generate a template project. Have Fun!"
                )
            }

            get("/api/latest/version") {
                val response = Http.get<LatestVersion>(VERSION_URL)
                call.respondText(response.version)
            }

            post("/api/generate") {
                val id = UUID.randomUUID().toString()
                val form = call.receiveParameters()
                val desForm = form.toMap()
                val projectType = ProjectType.fromString(desForm["type"]?.first().toString()) ?: return@post
                val projectName = desForm["projectName"]?.first() ?: return@post
                val groupId = desForm["group"]?.first() ?: return@post
                val robVersion = desForm["robVersion"]?.first() ?: return@post
                val kotlinVersion = desForm["kotlinVersion"]?.first() ?: return@post
                val extraFeatures =
                    desForm["features"]?.first()?.split(",")?.mapNotNull { ExtraFeature.fromList(it) } ?: return@post
                val targetBytes = generateProject(
                    projectType, groupId, projectName,
                    kotlinVersion, robVersion, id, extraFeatures
                )
                call.respondBytes(targetBytes, contentType = ContentType.parse("application/zip"))
            }
        }
    }.start(true)
}