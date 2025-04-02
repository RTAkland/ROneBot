/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.buildSrc

import java.io.File

private fun getShortCommitId(): String {
    val process = ProcessBuilder("git", "rev-parse", "--short=7", "HEAD")
        .directory(File("."))
        .start()
    return process.inputStream.bufferedReader().readText().trim()
}

private val internalPublishVersion = File("gradle.properties")
    .readLines().first { it.startsWith("libVersion") }
    .split("=").last()

val publishVersion = if (System.getenv("RTAST_PUBLISH_PASSWORD") == null) internalPublishVersion
else "$internalPublishVersion-${getShortCommitId()}"
