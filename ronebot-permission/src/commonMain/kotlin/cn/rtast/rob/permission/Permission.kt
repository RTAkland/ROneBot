/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */

@file:Suppress("unused")

package cn.rtast.rob.permission

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.entity.ISender
import cn.rtast.rob.permission.enums.BasicPermission

/**
 * 在非brigadier注册的命令中来判断是否有权限
 */
public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.hasPermission(
    userId: String,
    permission: BasicPermission
): Boolean {
    return permissionManager.hasPermission(userId, permission)
}

public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.hasPermission(
    userId: String,
    permNode: String
): Boolean {
    return permissionManager.hasPermission(userId, permNode)
}

public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.hasPermission(userId: String, level: Int): Boolean {
    return permissionManager.hasPermission(userId, BasicPermission.fromLevel(level))
}

/**
 * 在命令上下文中设置用户的权限
 */
public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.setPermission(
    userId: String,
    permission: BasicPermission
) {
    permissionManager.setUserPermission(userId, permission)
}

public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.setPermission(userId: String, permNode: String) {
    permissionManager.setUserPermission(userId, permNode)
}

public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.setPermission(userId: String, level: Int) {
    permissionManager.setUserPermission(userId, BasicPermission.fromLevel(level))
}

/**
 * 撤销一个用户的[BasicPermission]和权限等级
 */
public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.revokePermission(userId: String) {
    permissionManager.revokeUserPermission(userId)
}

/**
 * 撤销一个用户的权限节点
 */
public fun <T : IBaseCommand<IGroupMessage, IPrivateMessage>> T.revokePermission(userId: String, permNode: String) {
    permissionManager.revokeUserPermission(userId, permNode)
}

/**
 * 给Sender的子类添一个拓展函数用于直接对
 * 发送者对象判断是否有权限
 */
public fun <T : ISender> T.hasPermission(permission: BasicPermission): Boolean {
    return permissionManager.hasPermission(this.userId.toString(), permission)
}

public fun <T : ISender> T.hasPermission(permNode: String): Boolean {
    return permissionManager.hasPermission(this.userId.toString(), permNode)
}

public fun <T : ISender> T.hasPermission(level: Int): Boolean {
    return permissionManager.hasPermission(this.userId.toString(), BasicPermission.fromLevel(level))
}

/**
 * 给一个发送者设置权限
 */
public fun <T : ISender> T.setPermission(permission: BasicPermission) {
    permissionManager.setUserPermission(this.userId.toString(), permission)
}

public fun <T : ISender> T.setPermission(permNode: String) {
    permissionManager.setUserPermission(this.userId.toString(), permNode)
}

public fun <T : ISender> T.setPermission(level: Int) {
    permissionManager.setUserPermission(this.userId.toString(), BasicPermission.fromLevel(level))
}

/**
 * 撤销一个用户的[BasicPermission]和权限等级
 */
public fun <T : ISender> T.revokePermission() {
    permissionManager.revokeUserPermission(this.userId.toString())
}

/**
 * 撤销一个用户的权限节点
 */
public fun <T : ISender> T.revokePermission(permNode: String) {
    permissionManager.revokeUserPermission(this.userId.toString(), permNode)
}
