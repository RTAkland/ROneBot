/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 11:02 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.command

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.milky.event.ws.raw.ReceiveMessage

public abstract class BaseCommand : IBaseCommand<ReceiveMessage, ReceiveMessage> {
    public enum class ExecuteType {
        Group, Private, Temp
    }

    public abstract val type: List<ExecuteType>

    public abstract suspend fun onExecute(message: ReceiveMessage, type: ExecuteType, args: List<String>)
    public override suspend fun executeGroup(message: ReceiveMessage, args: List<String>) {}
    public override suspend fun executePrivate(message: ReceiveMessage, args: List<String>) {}
    public override suspend fun executeTemp(message: ReceiveMessage, args: List<String>) {}

    // 下面都没用

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun handleGroup(message: ReceiveMessage, matchedCommand: String) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun handlePrivate(message: ReceiveMessage, matchedCommand: String) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun onGroupSession(message: ReceiveMessage) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun onGroupSession(message: ReceiveMessage, initArg: Any) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun onPrivateSession(message: ReceiveMessage) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun onPrivateSession(message: ReceiveMessage, initArg: Any) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun ReceiveMessage.reject(reason: IMessageChain) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun ReceiveMessage.skipSession() {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun <T : Any> ReceiveMessage.startSession(initArg: T) {
    }

    @Deprecated("没用", level = DeprecationLevel.HIDDEN)
    public override suspend fun ReceiveMessage.startSession() {
    }
}