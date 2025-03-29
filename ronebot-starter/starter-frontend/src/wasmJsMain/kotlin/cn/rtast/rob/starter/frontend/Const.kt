/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.frontend

import io.ktor.client.HttpClient

public val client: HttpClient = HttpClient()

public const val defaultROBVersion: String = "2.9.2"
public const val defaultGradleVersion: String = "8.13"
public const val defaultKotlinVersion: String = "2.1.20"