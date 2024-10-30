/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */


package cn.rtast.rob.util.command

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.first
import cn.rtast.rob.enums.Permission
import cn.rtast.rob.enums.UserRole
import cn.rtast.rob.util.ob.OneBotListener

abstract class PermissionCommand {
    abstract val commandNames: List<String>

    /**
     * 定义需要的权限
     */
    abstract val permissions: List<Permission>

    open suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {}

    open suspend fun executeGroup(
        listener: OneBotListener,
        message: GroupMessage,
        args: List<String>,
        matchedCommand: String
    ) {
    }

    open suspend fun executePrivate(listener: OneBotListener, message: PrivateMessage, args: List<String>) {}

    open suspend fun executePrivate(
        listener: OneBotListener,
        message: PrivateMessage,
        args: List<String>,
        matchedCommand: String
    ) {
    }

    /**
     * 在群聊中没有执行权限时触发
     */
    abstract suspend fun noPermission(listener: OneBotListener, message: GroupMessage)

    internal fun hasPermission(senderRole: UserRole, requiredRole: Permission): Boolean {
        return senderRole.order >= requiredRole.order
    }

    internal suspend fun handlePrivate(listener: OneBotListener, message: PrivateMessage, matchedCommand: String) {
        val args = message.first.split(" ").drop(1)
        this.executePrivate(listener, message, args)
        this.executePrivate(listener, message, args, matchedCommand)
    }

    internal suspend fun handleGroup(listener: OneBotListener, message: GroupMessage, matchedCommand: String) {
        val requiredRole = permissions.minByOrNull { it.order } ?: Permission.MEMBER
        if (!hasPermission(message.sender.role, requiredRole)) {
            noPermission(listener, message)
            return
        }
        val args = message.first.split(" ").drop(1)
        this.executeGroup(listener, message, args)
        this.executeGroup(listener, message, args, matchedCommand)
    }
}