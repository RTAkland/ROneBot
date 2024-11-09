/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage

/**
 * 内置的指令管理器, 可以分别处理群聊和私聊中的指令
 */
interface CommandManager {
    /**
     * 存储普通的命令
     */
    val commands: MutableList<BaseCommand>

    /**
     * 注册一个命令
     */
    suspend fun register(command: BaseCommand)

    /**
     * 私聊中触发
     */
    suspend fun handlePrivate(message: PrivateMessage)

    /**
     * 群聊中触发
     */
    suspend fun handleGroup(message: GroupMessage)
}