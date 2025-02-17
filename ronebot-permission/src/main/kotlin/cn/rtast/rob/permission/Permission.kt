/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */

@file:Suppress("unused")

package cn.rtast.rob.permission

import cn.rtast.rob.command.ICommandSource
import cn.rtast.rob.entity.IBaseCommand
import cn.rtast.rob.entity.ISender
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

/**
 * 在Brigadier的source中设置用户权限
 */
fun <T : ICommandSource> T.setPermission(level: Int) {
    permissionManager.setUserPermission(this.firedUser.id, BasicPermission.fromLevel(level))
}

fun <T : ICommandSource> T.setPermission(permission: BasicPermission) {
    permissionManager.setUserPermission(this.firedUser.id, permission)
}

fun <T : ICommandSource> T.setPermission(permNode: String) {
    permissionManager.setUserPermission(this.firedUser.id, permNode)
}

/**
 * 撤销一个用户的[BasicPermission]和权限等级
 */
fun <T : ICommandSource> T.revokePermission() {
    permissionManager.revokeUserPermission(this.firedUser.id)
}

/**
 * 撤销一个用户的权限节点
 */
fun <T : ICommandSource> T.revokePermission(permNode: String) {
    permissionManager.revokeUserPermission(this.firedUser.id, permNode)
}

/**
 * 在非brigadier注册的命令中来判断是否有权限
 */
fun <T : IBaseCommand> T.hasPermission(userId: String, permission: BasicPermission): Boolean {
    return permissionManager.hasPermission(userId, permission)
}

fun <T : IBaseCommand> T.hasPermission(userId: String, permNode: String): Boolean {
    return permissionManager.hasPermission(userId, permNode)
}

fun <T : IBaseCommand> T.hasPermission(userId: String, level: Int): Boolean {
    return permissionManager.hasPermission(userId, BasicPermission.fromLevel(level))
}

/**
 * 在命令上下文中设置用户的权限
 */
fun <T : IBaseCommand> T.setPermission(userId: String, permission: BasicPermission) {
    permissionManager.setUserPermission(userId, permission)
}

fun <T : IBaseCommand> T.setPermission(userId: String, permNode: String) {
    permissionManager.setUserPermission(userId, permNode)
}

fun <T : IBaseCommand> T.setPermission(userId: String, level: Int) {
    permissionManager.setUserPermission(userId, BasicPermission.fromLevel(level))
}

/**
 * 撤销一个用户的[BasicPermission]和权限等级
 */
fun <T : IBaseCommand> T.revokePermission(userId: String) {
    permissionManager.revokeUserPermission(userId)
}

/**
 * 撤销一个用户的权限节点
 */
fun <T : IBaseCommand> T.revokePermission(userId: String, permNode: String) {
    permissionManager.revokeUserPermission(userId, permNode)
}

/**
 * 给Sender的子类添一个拓展函数用于直接对
 * 发送者对象判断是否有权限
 */
fun <T : ISender> T.hasPermission(permission: BasicPermission): Boolean {
    return permissionManager.hasPermission(this.userId.toString(), permission)
}

fun <T : ISender> T.hasPermission(permNode: String): Boolean {
    return permissionManager.hasPermission(this.userId.toString(), permNode)
}

fun <T : ISender> T.hasPermission(level: Int): Boolean {
    return permissionManager.hasPermission(this.userId.toString(), BasicPermission.fromLevel(level))
}

/**
 * 给一个发送者设置权限
 */
fun <T : ISender> T.setPermission(permission: BasicPermission) {
    permissionManager.setUserPermission(this.userId.toString(), permission)
}

fun <T : ISender> T.setPermission(permNode: String) {
    permissionManager.setUserPermission(this.userId.toString(), permNode)
}

fun <T : ISender> T.setPermission(level: Int) {
    permissionManager.setUserPermission(this.userId.toString(), BasicPermission.fromLevel(level))
}

/**
 * 撤销一个用户的[BasicPermission]和权限等级
 */
fun <T : ISender> T.revokePermission() {
    permissionManager.revokeUserPermission(this.userId.toString())
}

/**
 * 撤销一个用户的权限节点
 */
fun <T : ISender> T.revokePermission(permNode: String) {
    permissionManager.revokeUserPermission(this.userId.toString(), permNode)
}
