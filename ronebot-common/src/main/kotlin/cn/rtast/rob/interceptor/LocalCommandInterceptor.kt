/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.interceptor

import cn.rtast.rob.entity.IMessage

public interface IFunctionalLocalCommandInterceptor<M : IMessage> {
    public suspend fun before(message: M): CommandExecutionResult =
        CommandExecutionResult.CONTINUE

    public suspend fun after(message: M) {}

    public suspend fun handleInterceptor(message: M, block: suspend (M) -> Unit) {

        if (this.before(message) == CommandExecutionResult.CONTINUE) {
            try {
                block(message)
            } finally {
                this.after(message)
            }
        }
    }
}
