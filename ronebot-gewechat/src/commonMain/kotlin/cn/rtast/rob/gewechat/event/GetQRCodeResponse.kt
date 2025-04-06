/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:40
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("CLASSNAME")


package cn.rtast.rob.gewechat.event

import cn.rtast.rob.gewechat.exceptions.QRCodeImageDecodeFailedException
import cn.rtast.rob.util.decodeToByteArrayBase64
import com.ashampoo.kim.Kim
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

        public fun printToConsole() {
            TODO("暂未完成")
            val metadata = Kim.readMetadata(qrImageBase64.decodeToByteArrayBase64())
                ?: throw QRCodeImageDecodeFailedException("二维码图片有误")
        }

        private fun printQRCode(pixels: Array<IntArray>, width: Int, height: Int) {
            TODO("暂未完成")
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val isBlack = pixels[y][x] == 1
                    print(if (isBlack) "🖤" else "🤍")
                }
                println()
            }
        }
    }
}