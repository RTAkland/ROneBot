/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

import kotlinx.serialization.Serializable

/**
 * 表示LLOneBot的拓展API的枚举类
 */
@Serializable
public enum class OnlineStatus(public val statusCode: Int, public val desc: String) {
    Online(10, "在线"), AWAY(30, "离开"), HIDDEN(40, "隐身"),
    BUSY(50, "忙碌"), Q_ME(60, "Q我吧"), DO_NOT_DISTURB(70, "请勿打扰")
}