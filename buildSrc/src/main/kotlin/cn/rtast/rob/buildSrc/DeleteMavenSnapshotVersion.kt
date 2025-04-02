/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.buildSrc

import cn.rtast.rob.buildSrc.util.Http
import cn.rtast.rob.buildSrc.util.encodeToBase64

private val mavenPassword = System.getenv("RTAST_PUBLISH_PASSWORD") ?: throw Exception("没有密码")
private const val mavenUsername = "RTAkland"
private const val MAVEN_URL = "https://repo.maven.rtast.cn/api/auth/me"

fun deleteSnapshotVersion() {
    val header = "$mavenUsername:$mavenPassword".encodeToBase64()
    Http.delete("$MAVEN_URL/snapshots/cn/rtast/rob", null, mapOf("Authorization" to "Basic $header"))
}