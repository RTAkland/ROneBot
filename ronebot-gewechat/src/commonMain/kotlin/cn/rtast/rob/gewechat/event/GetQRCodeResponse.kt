/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:40
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("CLASSNAME")


package cn.rtast.rob.gewechat.event

import cn.rtast.rob.util.decodeToByteArrayBase64
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.Serializable

@Serializable
public data class GetQRCodeResponse(
    val data: QRCodeResponse
) {
    @Serializable
    public data class QRCodeResponse(
        val appId: String,
        val qrData: String,
        val qrImageBase64: String,
        val uuid: String
    ) {
        public fun saveTo(path: Path) {
            val buffer = Buffer().apply { write(qrImageBase64.decodeToByteArrayBase64()) }
            SystemFileSystem.sink(path).use { it.write(buffer, buffer.size) }
        }
    }
}