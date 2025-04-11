/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 22:41
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session.v2

import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IPrivateSender

public interface ISessionManager<G : IGroupSender, P : IPrivateSender> {
    public fun getGroupSessionStatus(creator: SessionCreator<G>): Boolean
    public fun getPrivateSessionStatus(creator: SessionCreator<P>): Boolean

    public fun invokeGroupSession(creator: SessionCreator<G>)
    public fun invokePrivateSession(creator: SessionCreator<P>)

    public fun <T : Any> startGroupSession(creator: SessionCreator<G>, initialSessionData: T, invokable: (T?) -> Unit)
    public fun <T : Any> startPrivateSession(creator: SessionCreator<P>, initialSessionData: T, invokable: (T?) -> Unit)
}