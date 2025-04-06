/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:26
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.gewechat

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

internal val httpClient = HttpClient(CIO)

internal const val QRCODE_GENERATOR_URL = "https://api.rtast.cn/api/generate_qr"