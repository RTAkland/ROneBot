/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import kotlin.uuid.ExperimentalUuidApi


@ExperimentalROneBotApi
public class SessionManager : ISessionManager<GroupMessage, PrivateMessage> {
    override val privateSessions: MutableMap<PrivateMessage, IPrivateSession<PrivateMessage>> = mutableMapOf()
    override val groupSessions: MutableMap<GroupMessage, IGroupSession<GroupMessage>> = mutableMapOf()
    override suspend fun handleGroupSession(
        message: GroupMessage,
        args: List<String>,
    ) {
        groupSessions[message]?.consume(GroupSessionStruct(args, message))
    }

    override suspend fun handlePrivateSession(
        message: PrivateMessage,
        args: List<String>,
    ) {
        privateSessions[message]?.consume(PrivateSessionStruct(args, message))
    }
}