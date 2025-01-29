/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */

@file:Suppress("unused")

package cn.rtast.rob.permission

import cn.rtast.rob.command.ICommandSource
import cn.rtast.rob.permission.enums.BasicPermission


/**
 * 使用权限等级[Int]来判断是否有权限
 */
fun <T : ICommandSource> T.hasPermission(level: Int): Boolean {
    return permissionManager.hasPermission(this.firedUser.id, BasicPermission.fromLevel(level))
}

/**
 * 使用权限等级[BasicPermission]来判断是否有权限
 */
fun <T : ICommandSource> T.hasPermission(permission: BasicPermission): Boolean {
    return permissionManager.hasPermission(this.firedUser.id, permission)
}

/**
 * 使用权限节点等级[String]来判断是否有权限
 */
fun <T : ICommandSource> T.hasPermission(permNode: String): Boolean {
    return permissionManager.hasPermission(this.firedUser.id, permNode)
}
