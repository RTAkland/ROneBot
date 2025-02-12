/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */

@file:Suppress("unused")

package cn.rtast.rob.permission

import cn.rtast.rob.BotFactory
import cn.rtast.rob.permission.enums.BasicPermission

fun <T : BotFactory> T.getPermissionManager() = permissionManager

val permissionManager by lazy { PermissionManager() }

class PermissionManager {

    private val userPermissions: MutableMap<String, BasicPermission> = mutableMapOf()
    private val userPermissionNodes: MutableMap<String, MutableSet<String>> = mutableMapOf()

    /**
     * 使用内置的[BasicPermission]枚举类来确定一个用户的权限
     */
    fun setUserPermission(userId: String, permission: BasicPermission) {
        userPermissions[userId] = permission
    }

    /**
     * 通过权限节点来确定用户权限
     */
    fun setUserPermission(userId: String, permissionNode: String) {
        userPermissionNodes.computeIfAbsent(userId) { mutableSetOf() }.add(permissionNode)
    }

    /**
     * 通过权限等级来确定用户权限
     */
    fun setUserPermission(userId: String, level: Int) {
        userPermissions[userId] = BasicPermission.fromLevel(level)
    }

    /**
     * 通过内置的[BasicPermission]判断是否拥有权限
     */
    internal fun hasPermission(userId: String, requiredPermission: BasicPermission): Boolean {
        val userPermission = userPermissions[userId] ?: BasicPermission.Other
        if (userPermission == BasicPermission.Owner) {
            return true
        }
        return userPermission.level >= requiredPermission.level
    }

    /**
     * 通过权限节点判断是否拥有权限
     */
    internal fun hasPermission(userId: String, permissionNode: String): Boolean {
        val userPermission = userPermissions[userId] ?: BasicPermission.Other
        if (userPermission == BasicPermission.Owner) {
            return true
        }
        return userPermissionNodes[userId]?.contains(permissionNode) == true
    }
}