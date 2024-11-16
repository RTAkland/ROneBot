/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.actionable

import cn.rtast.rob.qqbot.entity.Markdown

interface C2CMessageActionable {
    suspend fun reply(message: String)
    suspend fun reply(message: Markdown)
}

interface GroupMessageActionable : C2CMessageActionable {

}