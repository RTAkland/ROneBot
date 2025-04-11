/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 22:50
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session.v2

import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateSender

public class DefaultSessionManager : ISessionManager<GroupSender, PrivateSender> {
    private val groupSessions = mutableMapOf<SessionCreator<GroupSender>, GroupSessionContextImpl<*>>()
    private val privateSessions = mutableMapOf<SessionCreator<PrivateSender>, PrivateSessionContextImpl<*>>()

    override fun getGroupSessionStatus(creator: SessionCreator<GroupSender>): Boolean {
        return groupSessions[creator]?.isActive == true
    }

    override fun getPrivateSessionStatus(creator: SessionCreator<PrivateSender>): Boolean {
        return privateSessions[creator]?.isActive == true
    }

    override fun invokeGroupSession(creator: SessionCreator<GroupSender>) {
        this.getGroupSession<Any?>(creator)?.invokable(null)
    }

    override fun invokePrivateSession(creator: SessionCreator<PrivateSender>) {
        this.getPrivateSession<Any?>(creator)?.invokable(null)
    }

    override fun <T : Any> startGroupSession(
        creator: SessionCreator<GroupSender>,
        initialSessionData: T,
        invokable: (T?) -> Unit
    ) {
        groupSessions[creator] = GroupSessionContextImpl(creator, initialSessionData, true, invokable)
    }

    override fun <T : Any> startPrivateSession(
        creator: SessionCreator<PrivateSender>,
        initialSessionData: T,
        invokable: (T?) -> Unit
    ) {
        privateSessions[creator] = PrivateSessionContextImpl(creator, initialSessionData, true, invokable)
    }

    @Suppress("UNCHECKED_CAST")
    public fun <T : Any?> getGroupSession(creator: SessionCreator<GroupSender>): GroupSessionContextImpl<T>? {
        return groupSessions[creator] as GroupSessionContextImpl<T>?
    }

    @Suppress("UNCHECKED_CAST")
    public fun <T : Any?> getPrivateSession(creator: SessionCreator<PrivateSender>): PrivateSessionContextImpl<T>? {
        return privateSessions[creator] as PrivateSessionContextImpl<T>?
    }
}