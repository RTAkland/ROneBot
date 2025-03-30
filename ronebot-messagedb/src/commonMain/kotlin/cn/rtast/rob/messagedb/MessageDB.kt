/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.messagedb

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public class MessageDB {

    private var fileContent = mutableMapOf<Uuid, String>()

    public fun serialize(origin: String): MutableMap<Uuid, String> {
        return origin.split("\n").mapNotNull { line ->
            try {
                val kv = line.split(":")
                val uuid = Uuid.parse(kv.first())
                uuid to kv.last()
            } catch (_: Exception) {
                null
            }
        }.toMap().toMutableMap()
    }

    public fun deserialize(): String {
        return buildString {
            fileContent.forEach { (k, v) ->
                append("$k:$v\n")
            }
        }
    }

    public fun saveMessage(uuid: Uuid, messageId: String) {
        fileContent[uuid] = messageId
    }

    @Deprecated(message = "不支持移除消息", level = DeprecationLevel.HIDDEN)
    public fun removeMessage(): Nothing = TODO("不支持移除消息")


    public fun getMessage(uuid: Uuid): String? {
        return fileContent[uuid]
    }
}

/**
 * 使用自定义路径创建
 */
public fun createMessageDB(): MessageDB = MessageDB()