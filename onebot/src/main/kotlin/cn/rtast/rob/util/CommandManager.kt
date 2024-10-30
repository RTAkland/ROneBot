/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.ob.OneBotListener

interface CommandManager {
    /**
     * 存储普通的命令
     */
    val commands: MutableList<BaseCommand>

    /**
     * 存储带有权限控制的命令
     */
    val permissionCommands: MutableList<PermissionCommand>

    /**
     * 注册一个命令
     */
    suspend fun register(command: BaseCommand)

    /**
     * 注册一个带有权限控制的指令
     */
    suspend fun register(command: PermissionCommand)

    /**
     * 私聊中触发
     */
    suspend fun handlePrivate(listener: OneBotListener, message: PrivateMessage)

    /**
     * 群聊中触发
     */
    suspend fun handleGroup(listener: OneBotListener, message: GroupMessage)
}