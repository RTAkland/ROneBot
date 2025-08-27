/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:33 AM
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
 * 邀请自己入群请求Json解析
 */
@Serializable
public data class RawGroupFileUploadEvent(
    val data: GroupFileUpload,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupFileUpload(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发送者 QQ 号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * 文件 ID
         */
        @SerialName("file_id")
        val fileId: String,
        /**
         * 	文件名称
         */
        @SerialName("file_name")
        val fileName: String,
        /**
         * 文件大小
         */
        @SerialName("file_size")
        val fileSize: Long
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
            val url = action.getGroupFileDownloadUrl(groupId, fileId)
                .successOrNull()
            return if (url == null) {
                throw IllegalStateException("文件不存在")
            } else {
                httpClient.get(url.downloadUrl).bodyAsBytes()
            }
        }
    }
}