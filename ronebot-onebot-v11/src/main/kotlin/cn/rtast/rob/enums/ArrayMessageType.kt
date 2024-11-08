/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused", "EnumEntryName")

package cn.rtast.rob.enums

/**
 * 普通消息类型
 */
enum class ArrayMessageType {
    text, image, face, record,
    at, rps, shake, poke, share,
    reply, json, forward, dice,
    video, contact, location,
    music, xml, mface, node
}