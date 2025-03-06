/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.interceptor

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage
import kotlin.reflect.KFunction

/**
 * 函数式命令的局部拦截器
 */
public interface IFunctionalLocalCommandInterceptor<M : IMessage> {
    /**
     * 指令执行之前
     */
    public suspend fun before(message: M): CommandExecutionResult = CommandExecutionResult.CONTINUE

    /**
     * 指令执行之后
     */
    public suspend fun after(message: M) {}

    /**
     * 判断是否继续执行的具体实现
     */
    public suspend fun handleInterceptor(message: M, block: suspend (M) -> Unit) {}
}

/**
 * 函数是命令的全局拦截器
 */
public interface IFunctionalGlobalCommandInterceptor<G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 群聊执行之前
     * @param func 触发拦截器的函数式指令
     */
    public suspend fun beforeGroup(message: G, func: KFunction<*>): CommandExecutionResult =
        CommandExecutionResult.CONTINUE

    /**
     * 群聊执行之后
     * @param func 触发拦截器的函数式指令
     */
    public suspend fun afterGroup(message: G, func: KFunction<*>) {}

    /**
     * 私聊执行之前
     */
    public suspend fun beforePrivate(message: P, func: KFunction<*>): CommandExecutionResult =
        CommandExecutionResult.CONTINUE

    /**
     * 私聊执行之后
     * @param func 触发拦截器的函数式指令
     */
    public suspend fun afterPrivate(message: P, func: KFunction<*>) {}

    /**
     * 群聊拦截器的具体实现
     * @param func 触发拦截器的函数式指令
     */
    public suspend fun handleGroupInterceptor(message: G, func: KFunction<*>, block: suspend (G) -> Unit) {
        if (this.beforeGroup(message, func) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                this.afterGroup(message, func)
            }
        }
    }

    /**
     * 私聊拦截器的具体是西安
     * @param func 触发拦截器的函数式指令
     */
    public suspend fun handlePrivateInterceptor(message: P, func: KFunction<*>, block: suspend (P) -> Unit) {
        if (this.beforePrivate(message, func) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                this.afterPrivate(message, func)
            }
        }
    }
}