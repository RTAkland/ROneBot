/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.enums


import cn.rtast.rob.satori.enums.ChannelType.CATEGORY
import cn.rtast.rob.satori.enums.ChannelType.DIRECT
import cn.rtast.rob.satori.enums.ChannelType.TEXT
import cn.rtast.rob.satori.enums.ChannelType.VOICE

/**
 * 群组(频道)类型
 */
enum class ChannelType(val type: Int) {
    TEXT(0), DIRECT(1), CATEGORY(2), VOICE(3);

    companion object {
        fun forType(type: Int): ChannelType {
            return when (type) {
                DIRECT.type -> DIRECT
                CATEGORY.type -> CATEGORY
                VOICE.type -> VOICE
                else -> TEXT
            }
        }
    }
}