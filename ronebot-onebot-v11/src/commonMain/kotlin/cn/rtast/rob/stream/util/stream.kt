/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 5:36 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.stream.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.stream.StreamRequestStruct
import cn.rtast.rob.stream.StreamType
import cn.rtast.rob.stream.exception.StreamAPIException
import cn.rtast.rob.util.fromJson
import kotlinx.coroutines.channels.Channel

internal suspend fun Channel<String>.collectMessages(): List<String> {
    val results = mutableListOf<String>()
    for (msg in this) {
        results += msg
    }
    return results
}

internal suspend inline fun <reified T> Channel<String>.collectMessages(block: suspend (String) -> Unit): List<T> {
    val results = mutableListOf<T>()
    for (msg in this) {
        block(msg)
        results += msg.fromJson<T>()
    }
    return results
}

internal suspend inline fun <reified T> Channel<String>.collectAndCheck(): List<T> {
    return this.collectMessages<T> {
        if (it.fromJson<StreamRequestStruct>().data.type == StreamType.Error) {
            throw StreamAPIException()
        }
    }
}