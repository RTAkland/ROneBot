/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.PrivateSender
import kotlin.uuid.ExperimentalUuidApi

private typealias SM = ISessionManager<PrivateMessage, GroupMessage, PrivateSession<*>, GroupSession<*>, BaseCommand, GroupSender, PrivateSender>

public class SessionManager : SM {
    override val privateActiveSessions: MutableMap<PrivateSender, PrivateSession<*>> =
        mutableMapOf<PrivateSender, PrivateSession<*>>()
    override val groupActiveSessions: MutableMap<GroupSender, GroupSession<*>> =
        mutableMapOf<GroupSender, GroupSession<*>>()

    override suspend fun startGroupSession(message: GroupMessage, command: BaseCommand): GroupSession<*> {
        return this.starterGroupSession(message, command, Unit)
    }

    override suspend fun <T : Any> starterGroupSession(
        message: GroupMessage,
        command: BaseCommand,
        initArg: T
    ): GroupSession<T> {
        val session = GroupSession(message.sessionId!!, message, command, message.sender, initArg)
        groupActiveSessions[message.sender] = session
        return session
    }

    override suspend fun startPrivateSession(message: PrivateMessage, command: BaseCommand): PrivateSession<*> {
        return this.startPrivateSession(message, command, Unit)
    }

    override suspend fun <T : Any> startPrivateSession(
        message: PrivateMessage,
        command: BaseCommand,
        initArg: T
    ): PrivateSession<T> {
        val session = PrivateSession(message.sessionId!!, message, command, message.sender, initArg)
        privateActiveSessions[message.sender] = session
        return session
    }

    override suspend fun getPrivateSession(sender: PrivateSender): PrivateSession<*>? {
        return privateActiveSessions[sender]
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> getTypedPrivateSession(sender: PrivateSender): IPrivateSession<T>? {
        return privateActiveSessions[sender] as PrivateSession<T>
    }

    override suspend fun getGroupSession(sender: GroupSender): GroupSession<*>? {
        return groupActiveSessions[sender]
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> getTypedGroupSession(sender: GroupSender): IGroupSession<T>? {
        return groupActiveSessions[sender] as GroupSession<T>
    }
}