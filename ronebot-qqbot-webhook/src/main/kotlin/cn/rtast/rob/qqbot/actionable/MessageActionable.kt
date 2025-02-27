/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.actionable

import cn.rtast.rob.qqbot.segment.Keyboard
import cn.rtast.rob.qqbot.segment.Markdown

/**
 * 单聊的可操作接口
 */
interface C2CMessageActionable {
    /**
     * 使用纯文本回复消息
     */
    suspend fun reply(message: String)

    /**
     * 使用Markdown回复消息
     */
    suspend fun reply(message: Markdown)

    /**
     * 使用键盘格回复消息
     */
    suspend fun reply(message: Keyboard)

    /**
     * 撤回消息
     */
    suspend fun revoke()
}

/**
 * 群聊的可操作接口
 */
interface GroupMessageActionable : C2CMessageActionable {

}