/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused", "EnumEntryName")

package cn.rtast.rob.enums

import kotlinx.serialization.Serializable

/**
 * 普通消息类型
 */
@Serializable
public enum class SegmentType {
    text, image, face, record,
    at, rps, shake, poke, share,
    reply, json, forward, dice,
    video, contact, location,
    music, xml, mface, node, file,
    markdown
}

/**
 * 为了兼容旧版本的sdk而创建的类型别名
 */
public typealias ArrayMessageType = SegmentType