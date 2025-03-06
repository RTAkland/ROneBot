/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.interceptor

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage

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


public interface IFunctionalGlobalCommandInterceptor<G : IGroupMessage, P : IPrivateMessage> {
    public suspend fun beforeGroup(message: G): CommandExecutionResult = CommandExecutionResult.CONTINUE

    public suspend fun afterGroup(message: G) {}

    public suspend fun beforePrivate(message: P): CommandExecutionResult = CommandExecutionResult.CONTINUE

    public suspend fun afterPrivate(message: P) {}

    public suspend fun handleGroupInterceptor(message: G, block: suspend (G) -> Unit)

    public suspend fun handlePrivateInterceptor(message: P, block: suspend (P) -> Unit)
}