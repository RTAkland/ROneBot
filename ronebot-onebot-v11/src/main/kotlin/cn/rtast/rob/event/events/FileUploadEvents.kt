/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.lagrange.FileEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 群聊上传文件
 */
data class GroupFileUploadEvent(
    override val action: OneBotAction,
    val file: FileEvent
) : OneBotEvent

/**
 * 私聊发送文件
 */
data class PrivateFileUploadEvent(
    override val action: OneBotAction,
    val file: FileEvent
) : OneBotEvent