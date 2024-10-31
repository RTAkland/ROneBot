/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.first
import cn.rtast.rob.enums.Permission
import cn.rtast.rob.enums.UserRole

/**
 * 定义一个需要特定权限才能执行的命令, 这里的权限
 * 指的是QQ群内的权限例如`管理员` `群主` `成员`这三种,
 * 定义权限需要传入一个[Permission]对象, Permission中的三个权限
 * 对象分别对应了
 * 1. [Permission.OWNER] -> [UserRole.owner] (群主)
 * 2. [Permission.ADMIN] -> [UserRole.admin] (管理员)
 * 3. [Permission.MEMBER] -> [UserRole.member] (成员)
 * 在没有权限执行的时候会触发[PermissionCommand.noPermission]函数
 * 参数包含了一个触发此命令的消息对象, 可以对消息进行操作
 *
 * e.g.
 * class PCommand : PermissionCommand() {
 *     override val commandNames: List<String> = listOf("p")
 *     override val permissions: List<Permission> = listOf([Permission.OWNER], [Permission.ADMIN])
 *     override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
 *         println("Has permission")
 *     }
 *
 *     override suspend fun noPermission(
 *         message: GroupMessage
 *     ) {
 *         println("No permission")
 *     }
 * }
 */
abstract class PermissionCommand {
    abstract val commandNames: List<String>

    /**
     * 定义需要的权限, 传入一个列表, 取最低权限
     * e.g. listOf([Permission.OWNER], [Permission.MEMBER])
     * 表示只要是[Permission.MEMBER]都可以执行, [Permission.MEMBER] -> [UserRole.member]
     */
    abstract val permissions: List<Permission>

    /**
     * 群聊指令
     */
    open suspend fun executeGroup(message: GroupMessage, args: List<String>) {}

    /**
     * 群聊指令并且附带匹配到的命令
     */
    open suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {}

    /**
     * 私聊指令
     */
    open suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}

    /**
     * 私聊指令并且附带匹配到的命令
     */
    open suspend fun executePrivate(message: PrivateMessage, args: List<String>, matchedCommand: String) {}

    /**
     * 在群聊中没有执行权限时触发
     */
    abstract suspend fun noPermission(message: GroupMessage)

    /**
     * 判断[UserRole.order]和[Permission.order]的值来比较是否
     * 有权限执行命令
     */
    internal fun hasPermission(senderRole: UserRole, requiredPermission: Permission): Boolean {
        return senderRole.order >= requiredPermission.order
    }

    internal suspend fun handlePrivate(message: PrivateMessage, matchedCommand: String) {
        val args = message.first.split(" ").drop(1)
        this.executePrivate(message, args)
        this.executePrivate(message, args, matchedCommand)
    }

    internal suspend fun handleGroup(message: GroupMessage, matchedCommand: String) {
        val requiredRole = permissions.minByOrNull { it.order } ?: Permission.MEMBER
        if (!hasPermission(message.sender.role, requiredRole)) {
            noPermission(message)
            return
        }
        val args = message.first.split(" ").drop(1)
        this.executeGroup(message, args)
        this.executeGroup(message, args, matchedCommand)
    }
}