/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */

@file:Suppress("unused")

package cn.rtast.rob.permission

import cn.rtast.rob.BotFactory
import cn.rtast.rob.permission.enums.BasicPermission

public fun <T : BotFactory> T.getPermissionManager() = permissionManager

public val permissionManager: PermissionManager by lazy { PermissionManager() }

public class PermissionManager {

    private val userPermissions: MutableMap<String, BasicPermission> = mutableMapOf()
    private val userPermissionNodes: MutableMap<String, MutableSet<String>> = mutableMapOf()

    /**
     * 使用内置的[BasicPermission]枚举类来确定一个用户的权限
     */
    public fun setUserPermission(userId: String, permission: BasicPermission) {
        userPermissions[userId] = permission
    }

    /**
     * 通过权限节点来确定用户权限
     */
    public fun setUserPermission(userId: String, permissionNode: String) {
        val permissionSet = userPermissionNodes.getOrPut(userId) { mutableSetOf() }
        permissionSet.add(permissionNode)
    }

    /**
     * 通过权限等级来确定用户权限
     */
    public fun setUserPermission(userId: String, level: Int) {
        userPermissions[userId] = BasicPermission.fromLevel(level)
    }

    /**
     * 撤销一个用户的用户权限节点
     */
    public fun revokeUserPermission(userId: String, permNode: String): Boolean {
        return userPermissionNodes[userId]?.remove(permNode) == true
    }

    /**
     * 撤销一个用户的用户权限节点
     */
    public fun revokeUserPermission(userId: String): Boolean {
        try {
            userPermissions.remove(userId)
            return true
        } catch (_: NoSuchElementException) {
            return false
        }
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