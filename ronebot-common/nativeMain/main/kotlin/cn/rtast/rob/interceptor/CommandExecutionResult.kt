/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */

@file:Suppress("unused")

package cn.rtast.rob.interceptor

/**
 * 两种状态表示是否继续执行命令
 */
enum class CommandExecutionResult {
    CONTINUE, STOP
}