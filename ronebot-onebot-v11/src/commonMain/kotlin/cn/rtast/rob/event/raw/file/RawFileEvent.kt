/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/8
 */


@file:OptIn(ExperimentalROneBotApi::class, InternalROneBotApi::class)

package cn.rtast.rob.event.raw.file

import cn.rtast.rob.actionable.FileEventActionable
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.commonCoroutineScope
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

internal expect suspend fun saveFile(path: Path, bytes: ByteArray): Path

internal expect suspend fun readBytes(url: String): ByteArray

/**
 * Lagrange.OneBot的拓展Segment解析
 */
@Serializable
public data class RawFileEvent(
    @SerialName("group_id")
    val groupId: Long?,
    @SerialName("user_id")
    val userId: Long,
    val file: File,
) : FileEventActionable {

    @Transient
    lateinit var action: OneBotAction

    @Serializable
    public data class File(
        val id: String,
        val name: String,
        val size: Int,
        val url: String?,
        @SerialName("busid")
        val busId: Long,
    )

    /**
     * 分块保存文件
     */
    override suspend fun saveTo(path: Path): Path {
        return withContext(Dispatchers.Default) {
            saveFile(path, readBytes())
        }
    }

    override suspend fun saveToAsync(path: Path) {
        commonCoroutineScope.launch {
            saveFile(path, readBytes())
        }
    }

    override suspend fun readBytes(): ByteArray {
        val url = this@RawFileEvent.file.url ?: error("not found url in RawFileEvent.File")
        return withContext(Dispatchers.Default) {
            readBytes(url)
        }
    }
}