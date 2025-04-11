/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 22:37
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session.v2

import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.entity.IPrivateSender
import cn.rtast.rob.entity.ISender

public interface SessionContext<T : Any?, K : ISender> {
    public val sessionInitialData: T
    public val creator: SessionCreator<K>
    public val invokable: (T) -> Unit
    public var isActive: Boolean
    public suspend fun reject(reason: IMessageChain)
    public suspend fun reject() {
        // DO NOTHING
    }

    public suspend fun confirm(response: IMessageChain)
    public suspend fun confirm() {
        isActive = false
    }
}

public interface GroupSessionContext<T : Any?, K : IGroupSender> : SessionContext<T, K> {
    public override val creator: SessionCreator<K>
}

public interface PrivateSessionContext<T : Any?, K : IPrivateSender> : SessionContext<T, K> {
    override val creator: SessionCreator<K>
}