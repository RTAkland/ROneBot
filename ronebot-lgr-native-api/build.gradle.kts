import cn.rtast.rob.buildSrc.LIST_ARTIFACT_BASE_URL
import cn.rtast.rob.buildSrc.ListArtifact
import cn.rtast.rob.buildSrc.util.Http
import java.net.HttpURLConnection
import java.net.URI

/*
 * Copyright © 2025 RTAkland
 * Date: 9/27/25, 3:02 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kdef)
}

kotlin {
    mingwX64()
    linuxX64()
    linuxArm64()
    macosArm64()
}

val librariesPath = mapOf(
    "linux-arm64" to project.layout.projectDirectory.dir("src/cinterop/libs/libLagrange.Core.NativeAPI-linuxArm64.so").asFile,
    "win-x64" to project.layout.projectDirectory.dir("src/cinterop/libs/libLagrange.Core.NativeAPI-win64.dll").asFile,
    "osx-arm64" to project.layout.projectDirectory.dir("src/cinterop/libs/libLagrange.Core.NativeAPI-macosArm64.dylib").asFile,
    "linux-x64" to project.layout.projectDirectory.dir("src/cinterop/libs/libLagrange.Core.NativeAPI-linux64.so").asFile
)

tasks.register("downloadLibrary") {
    // GitHub personal access token
    val pat: String = System.getenv("PAT")
    var workflowRunId: Long? = null
    Http.get<ListArtifact>(LIST_ARTIFACT_BASE_URL, headers = mapOf("Authorization" to "token $pat"))
        .artifacts.filter { it.name.contains("NativeAPI") }.apply { workflowRunId = first().workflow.id }
        .forEach {
            if (workflowRunId == it.workflow.id) {
                println("Downloading ${it.name}")
                val url = URI(it.downloadUrl).toURL()
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("Authorization", "token $pat")
                librariesPath[it.name.split("NativeAPI-").last()]?.writeBytes(conn.getInputStream().readBytes())
                    ?: println("")
                Thread.sleep(1000L)
                println("${it.name} downloaded sleeping 1 sec\n")
            }
        }
}

