/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.interceptor

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.PrivateMessage

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
        block: suspend (GroupMessage) -> Unit
    ) {
        if (this.beforeGroup(message) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                this.afterGroup(message)
            }
        }
    }

    final override suspend fun handlePrivateInterceptor(
        message: PrivateMessage,
        block: suspend (PrivateMessage) -> Unit
    ) {
        if (this.beforePrivate(message) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                this.afterPrivate(message)
            }
        }
    }
}


internal val defaultFunctionalInterceptor = object : FunctionalGlobalCommandInterceptor() {}