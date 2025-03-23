/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */

@file:Suppress("unused", "EnumEntryName")

package cn.rtast.rob.enums

import kotlinx.serialization.Serializable

/**
 * 精华消息类型
 */
@Serializable
public enum class EssenceMessageType {
    video, text, image
}