/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.interceptor

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage

/**
 * 指令执行拦截器实现这个接口并且重写你需要的方法
 * e.g.
 * ```kotlin
 * class CustomInterceptor: ExecutionInterceptor() {
 *     override suspend fun beforeGroupExecute(message: GroupMessage, command: BaseCommand): CommandResult {
 *         println("before group command execute and continue")
 *         return CommandResult.CONTINUE
 *     }
 *
 *     override suspend fun afterGroupExecute(message: GroupMessage, command: BaseCommand) {
 *         println("after group command execute")
 *     }
 * }
 * ```
 */
public interface ICommandInterceptor<B : IBaseCommand<IGroupMessage, IPrivateMessage>, G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 在群组命令执行之前执行, 可以返回[CommandExecutionResult]中的枚举类
     * 来确定是否继续执行这条命令
     * @param command 触发拦截器的命令
     */
    public suspend fun beforeGroupExecute(message: G, command: B): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }

    /**
     * 群组命令执行之后要执行的代码片段
     * @param command 触发拦截器的命令
     */
    public suspend fun afterGroupExecute(message: G, command: B) {}

    /**
     * 在私聊命令执行之前执行, 可以返回[CommandExecutionResult]中的枚举类
     * 来确定是否继续执行这条命令
     * @param command 触发拦截器的命令
     */
    public suspend fun beforePrivateExecute(message: P, command: B): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }

    /**
     * 私聊命令执行之后要执行的代码片段
     * @param command 触发拦截器的命令
     */
    public suspend fun afterPrivateExecute(message: P, command: B) {}
}

public class Interceptor<B : IBaseCommand<IGroupMessage, IPrivateMessage>, G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 执行群聊的指令拦截器并且记录指令成功执行的次数
     */
    public suspend fun handleGroupInterceptor(
        message: G,
        interceptor: ICommandInterceptor<B, G, P>,
        command: B,
        block: suspend (G) -> Unit
    ) {
        if (interceptor.beforeGroupExecute(message, command) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                interceptor.afterGroupExecute(message, command)
            }
        }
    }

    /**
     * 执行私聊的指令拦截器并且记录指令成功执行的次数
     */
    public suspend fun handlePrivateInterceptor(
        message: P,
        interceptor: ICommandInterceptor<B, G, P>,
        command: B,
        block: suspend (P) -> Unit
    ) {
        if (interceptor.beforePrivateExecute(message, command) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                interceptor.afterPrivateExecute(message, command)
            }
        }
    }
}
