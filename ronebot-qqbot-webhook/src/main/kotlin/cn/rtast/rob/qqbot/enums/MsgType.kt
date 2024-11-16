/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.enums

enum class MsgType(val type: Int) {
    PlainText(0), Markdown(2), ARK(3),
    Embed(4), Media(7)
}