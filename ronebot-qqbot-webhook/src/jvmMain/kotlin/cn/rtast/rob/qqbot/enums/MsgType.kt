/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.enums

public enum class MsgType(public val type: Int) {
    PlainText(0), Markdown(2), ARK(3),
    Embed(4), Media(7)
}