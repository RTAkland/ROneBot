/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.interceptor

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage

public interface ICommandInterceptor<B : IBaseCommand<out IGroupMessage, out IPrivateMessage>, G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 在群组命令执行之前执行, 可以返回[CommandExecutionResult]中的枚举类
     * 来确定是否继续执行这条命令
     * @param command 触发拦截器的命令
     */
    public suspend fun beforeGroupExecute(message: G, command: B): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }

    public fun beforeGroupExecuteJvm(message: G, command: B): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }

    /**
     * 群组命令执行之后要执行的代码片段
     * @param command 触发拦截器的命令
     */
    public suspend fun afterGroupExecute(message: G, command: B) {}
    public fun afterGroupExecuteJvm(message: G, command: B) {}

    /**
     * 群组命令执行中捕捉的异常
     * @param command 触发拦截器的命令
     */
    public suspend fun catchGroupExecute(message: G, command: B, e: Exception) {}
    public fun catchGroupExecuteJvm(message: G, command: B, e: Exception) {}

    /**
     * 在私聊命令执行之前执行, 可以返回[CommandExecutionResult]中的枚举类
     * 来确定是否继续执行这条命令
     * @param command 触发拦截器的命令
     */
    public suspend fun beforePrivateExecute(message: P, command: B): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }

    public fun beforePrivateExecuteJvm(message: P, command: B): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }

    /**
     * 私聊命令执行之后要执行的代码片段
     * @param command 触发拦截器的命令
     */
    public suspend fun afterPrivateExecute(message: P, command: B) {}
    public fun afterPrivateExecuteJvm(message: P, command: B) {}

    /**
     * 私聊命令执行中捕获的异常
     * @param command 触发拦截器的命令
     */
    public suspend fun catchPrivateExecute(message: P, command: B, e: Exception) {}
    public fun catchPrivateExecuteJvm(message: P, command: B, e: Exception) {}

    /**
     * 执行群聊的指令拦截器并且记录指令成功执行的次数
     * @param command 触发拦截器的命令
     */
    public suspend fun handleGroupInterceptor(
        message: G,
        command: B,
        block: suspend (G) -> Unit
    ) {
        if (this.beforeGroupExecute(message, command) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } catch (e: Exception) {
                this.catchGroupExecute(message, command, e)
                throw e
            } finally {
                this.afterGroupExecute(message, command)
            }
        }
    }

    /**
     * 执行私聊的指令拦截器并且记录指令成功执行的次数
     * @param command 触发拦截器的命令
     */
    public suspend fun handlePrivateInterceptor(
        message: P,
        command: B,
        block: suspend (P) -> Unit
    ) {
        if (this.beforePrivateExecute(message, command) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } catch (e: Exception) {
                this.catchPrivateExecute(message, command, e)
                throw e
            } finally {
                this.afterPrivateExecute(message, command)
            }
        }
    }
}