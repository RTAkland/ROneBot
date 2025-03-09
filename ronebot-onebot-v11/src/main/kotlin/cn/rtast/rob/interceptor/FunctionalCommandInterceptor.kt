/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.interceptor

import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.event.raw.PrivateMessage
import kotlin.reflect.KFunction

public abstract class FunctionalCommandInterceptor<M : IMessage> : IFunctionalLocalCommandInterceptor<M> {
    final override suspend fun handleInterceptor(message: M, block: suspend (M) -> Unit) {
        if (this.before(message) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                this.after(message)
            }
        }
    }
}

public abstract class FunctionalGlobalCommandInterceptor :
    IFunctionalGlobalCommandInterceptor<GroupMessage, PrivateMessage> {
    final override suspend fun handleGroupInterceptor(
        message: GroupMessage,
        func: KFunction<*>,
        block: suspend (GroupMessage) -> Unit
    ): Unit = super.handleGroupInterceptor(message, func, block)


    final override suspend fun handlePrivateInterceptor(
        message: PrivateMessage,
        func: KFunction<*>,
        block: suspend (PrivateMessage) -> Unit
    ): Unit = super.handlePrivateInterceptor(message, func, block)

}

internal val defaultFunctionalInterceptor = object : FunctionalGlobalCommandInterceptor() {}