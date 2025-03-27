/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/8
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.actionable.FileEventActionable
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

internal fun saveFile(path: Path, bytes: ByteArray) {
    val buffer = Buffer().apply {
        write(bytes)
    }
    SystemFileSystem.sink(path).use {
        it.write(buffer, buffer.size)
    }
}

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
        val url: String,
        @SerialName("busid")
        val busId: Long,
    )

    /**
     * 分块保存文件
     */
    override suspend fun saveTo(file: Path) {
        withContext(Dispatchers.Default) {
            saveFile(file, readBytes())
        }
    }

    override suspend fun readBytes(): ByteArray {
        return withContext(Dispatchers.Default) {
            readBytes(this@RawFileEvent.file.url)
        }
    }
}