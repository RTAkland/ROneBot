/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/20
 */


package cn.rtast.rob.installer

import cn.rtast.rob.common.ext.Http
import cn.rtast.rob.installer.entity.Artifacts
import cn.rtast.rob.installer.entity.RunId
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.util.zip.ZipInputStream

class LagrangeInstaller(private val accessToken: String) : Installer {

    private val owner = "LagrangeDev"
    private val repo = "Lagrange.Core"

    override suspend fun fetchLatestVersion(): String {
        val runIdResponse = Http.get<RunId>(GITHUB_ACTION_API_URL.replace("{owner}", owner).replace("{repo}", repo))
        val artifactsUrl = runIdResponse.workflowRuns
            .first { it.name.contains("Build") && it.event == "push" }.artifactsUrl
        val artifactsDownloadUrl = Http.get<Artifacts>(artifactsUrl).artifacts.first().archiveDownloadUrl
        return artifactsDownloadUrl
    }

    override suspend fun download() {
        val assetsUrl = this.fetchLatestVersion()
        val connection = URI(assetsUrl).toURL().openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.doInput = true
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.setRequestProperty("Authorization", "Bearer $accessToken")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.inputStream.use { input ->
            FileOutputStream(File("./Lagrange.OneBot.zip")).use { output ->
                input.copyTo(output)
            }
        }
    }

    override suspend fun extract() {
        this.download()
        val file = File("./Lagrange.OneBot.zip")
        ZipInputStream(file.inputStream()).use { zipStream ->
            var entry = zipStream.nextEntry
            while (entry != null) {
                val filePath = "./Lagrange.OneBot"
                if (!entry.isDirectory) {
                    File(filePath).outputStream().use { output ->
                        zipStream.copyTo(output)
                    }
                } else {
                    File(filePath).mkdirs()
                }
                zipStream.closeEntry()
                entry = zipStream.nextEntry
            }
        }
        file.delete()
    }
}