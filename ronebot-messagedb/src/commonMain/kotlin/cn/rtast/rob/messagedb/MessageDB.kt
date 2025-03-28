/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.messagedb

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.ext.bytearray.ByteBuffer
import cn.rtast.rob.ext.bytearray.toLong
import cn.rtast.rob.ext.file.readByteArray
import cn.rtast.rob.ext.file.writeByteArray
import cn.rtast.rob.ext.uuid.lsb
import cn.rtast.rob.ext.uuid.msb
import cn.rtast.rob.ext.uuid.uuidOf
import kotlinx.io.files.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public class MessageDB(private val path: Path) {
    private var fileContent = path.readByteArray().serialize()

    private fun ByteArray.serialize(): MutableMap<Uuid, Long> {
        val result = mutableMapOf<Uuid, Long>()
        var position = 0
        while (position + 24 <= this.size) {
            val mostSigBits = this.toLong(position)
            val leastSigBits = this.toLong(position + 8)
            val uuid = uuidOf(mostSigBits, leastSigBits)
            val value = this.toLong(position + 16)
            result[uuid] = value
            position += 24
        }
        return result
    }

    private fun Map<Uuid, Long>.deserialize(): ByteArray {
        // uuid 最高位和最低位加起来占用16个字节, messageId占用8个字节
        val buffer = ByteArray(this.keys.size * 24)
        this.forEach { (uuid, messageId) ->
            buffer + ByteBuffer(24).apply {
                putLong(uuid.msb)
                putLong(uuid.lsb)
                putLong(messageId)
            }.toByteArray()
        }
        return buffer
    }

    public fun syncToFile() {
        path.writeByteArray(fileContent.deserialize())
    }

    public fun saveMessage(uuid: Uuid, messageId: Long) {
        fileContent[uuid] = messageId
    }

    @Deprecated(message = "不支持移除消息", level = DeprecationLevel.HIDDEN)
    public fun removeMessage(): Nothing = TODO("不支持移除消息")


    public fun getMessage(uuid: Uuid): Long? {
        return fileContent[uuid]
    }
}

/**
 * 使用默认的路径创建一个消息二进制文件
 */
public val BaseBotInstance.messageDB: MessageDB
    get() = createMessageDB()

/**
 * 使用自定义路径创建
 */
public fun BaseBotInstance.createMessageDB(
    path: Path = Path("./${this.hashCode()}.messages.bin")
): MessageDB = MessageDB(path)