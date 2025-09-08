/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:06 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.enums

import cn.rtast.rob.milky.command.BaseCommand
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 接收消息的类别
 */
@Serializable
public enum class MessageScene {
    /**
     * 私聊消息
     */
    @SerialName("friend")
    Friend,

    /**
     * 群聊消息
     */
    @SerialName("group")
    Group,

    /**
     * 临时消息
     */
    @SerialName("temp")
    Temp;

    public fun toExecuteType(): BaseCommand.ExecuteType {
        return when (this) {
            Friend -> BaseCommand.ExecuteType.Private
            Group -> BaseCommand.ExecuteType.Group
            Temp -> BaseCommand.ExecuteType.Temp
        }
    }
}