/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:54 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.actionable.FileEventActionable
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.httpClient
import cn.rtast.rob.milky.milky.MilkyAction
import cn.rtast.rob.milky.util.arrow.successOrNull
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 好友文件上传Json解析
 */
@Serializable
public data class RawFriendFileUploadEvent(
    val data: FriendFileUpload,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class FriendFileUpload(
        /**
         * 好友 QQ 号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * 文件 ID
         */
        @SerialName("file_id")
        val fileId: String,
        /**
         * 文件名称
         */
        @SerialName("file_name")
        val filename: String,
        /**
         * 文件大小
         */
        @SerialName("file_size")
        val fileSize: Long,
        /**
         * 是否是自己发送的文件
         */
        @SerialName("is_self")
        val isSelf: Boolean
    ) : FileEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmBlocking
        override suspend fun save(path: Path) {
            val bytes = this.readBytes()
            SystemFileSystem.sink(path).buffered().use { it.write(bytes) }
        }

        @JvmBlocking
        override suspend fun save(path: String) {
            this.save(Path(path))
        }

        @JvmBlocking
        override suspend fun readBytes(): ByteArray {
            val url = action.getPrivateFileDownloadUrl(userId, fileId)
                .successOrNull()
            return if (url == null) {
                throw IllegalStateException("文件不存在")
            } else {
                httpClient.get(url.downloadUrl).bodyAsBytes()
            }
        }
    }
}