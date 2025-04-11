/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 22:46
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session.v2

import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateSender
import cn.rtast.rob.onebot.MessageChain

public class GroupSessionContextImpl<T : Any?>(
    override val creator: SessionCreator<GroupSender>,
    override val sessionInitialData: T,
    override var isActive: Boolean,
    override val invokable: (T?) -> Unit
) : GroupSessionContext<T, GroupSender> {
    override suspend fun reject(reason: IMessageChain) {
        creator.sender.action.sendGroupMessageAsync(creator.sender.groupId, reason as MessageChain)
    }

    override suspend fun confirm(response: IMessageChain) {
        isActive = false
        this.reject(response)
    }
}

public class PrivateSessionContextImpl<T : Any?>(
    override val creator: SessionCreator<PrivateSender>,
    override val sessionInitialData: T,
    override var isActive: Boolean = true,
    override val invokable: (T?) -> Unit
) : PrivateSessionContext<T, PrivateSender> {
    override suspend fun reject(reason: IMessageChain) {
        creator.sender.action.sendPrivateMessageAsync(creator.sender.userId, reason as MessageChain)
    }

    override suspend fun confirm(response: IMessageChain) {
        isActive = false
        this.reject(response)
    }
}