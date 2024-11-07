/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.interceptor

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.Logger

private val logger = Logger.getLogger()

/**
 * 指令执行拦截器实现这个接口并且重写你需要的方法
 * e.g.
 * ```kotlin
 * class CustomInterceptor: ExecutionInterceptor {
 *     override suspend fun beforeGroupExecute(message: GroupMessage): CommandResult {
 *         println("before group command execute and continue")
 *         return CommandResult.CONTINUE
 *     }
 *
 *     override suspend fun afterGroupExecute(message: GroupMessage) {
 *         println("after group command execute")
 *     }
 * }
 * ```
 */
interface ExecutionInterceptor {
    /**
     * 在群组命令执行之前执行, 可以返回[CommandResult]中的枚举类
     * 来确定是否继续执行这条命令
     */
    suspend fun beforeGroupExecute(message: GroupMessage): CommandResult {
        return CommandResult.CONTINUE
    }

    /**
     * 群组命令执行之后要执行的代码片段
     */
    suspend fun afterGroupExecute(message: GroupMessage) {}

    /**
     * 在私聊命令执行之前执行, 可以返回[CommandResult]中的枚举类
     * 来确定是否继续执行这条命令
     */
    suspend fun beforePrivateExecute(message: PrivateMessage): CommandResult {
        return CommandResult.CONTINUE
    }

    /**
     * 私聊命令执行之后要执行的代码片段
     */
    suspend fun afterPrivateExecute(message: PrivateMessage) {}
}

/**
 * 执行群聊的指令拦截器并且记录指令成功执行的次数
 */
internal suspend fun handleGroupInterceptor(
    message: GroupMessage,
    interceptor: ExecutionInterceptor,
    block: suspend (GroupMessage) -> Unit
) {
    if (interceptor.beforeGroupExecute(message) == CommandResult.CONTINUE) {
        try {
            block(message)
        } finally {
            interceptor.afterGroupExecute(message)
        }
    } else {
        logger.debug("Group command execution(message: {}) was stopped by the interceptor.", message)
    }
}

/**
 * 执行私聊的指令拦截器并且记录指令成功执行的次数
 */
internal suspend fun handlePrivateInterceptor(
    message: PrivateMessage,
    interceptor: ExecutionInterceptor,
    block: suspend (PrivateMessage) -> Unit
) {
    if (interceptor.beforePrivateExecute(message) == CommandResult.CONTINUE) {
        try {
            block(message)
        } finally {
            interceptor.afterPrivateExecute(message)
        }
    } else {
        logger.debug("Private command execution(message: {}) was stopped by the interceptor.", message)
    }
}

/**
 * 当用户没有设置指令拦截器时使用默认的拦截器
 * 即: 继续执行任何指令, 执行完成之后不做任何操作
 */
internal val defaultInterceptor = object : ExecutionInterceptor {}
