/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

@file:Suppress("unused")

package cn.rtast.rob.annotations.command.functional.session

/**
 * 标记一个函数作为函数式指令的会话接收函数
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
public annotation class PrivateSessionHandler