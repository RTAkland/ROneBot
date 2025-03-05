/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.enums


/**
 * 群组(频道)类型
 */
public enum class ChannelType(public val type: Int) {
    TEXT(0), DIRECT(1), CATEGORY(2), VOICE(3);

    public companion object {
        public fun forType(type: Int): ChannelType {
            return when (type) {
                DIRECT.type -> DIRECT
                CATEGORY.type -> CATEGORY
                VOICE.type -> VOICE
                else -> TEXT
            }
        }
    }
}