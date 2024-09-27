/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.lagrange.FileEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.entity.metadata.ConnectEvent
import cn.rtast.rob.entity.metadata.HeartBeatEvent

interface OneBotListener : OneBotAction {
    suspend fun onWebsocketErrorEvent(ex: Exception) {}
    suspend fun onWebsocketOpenEvent() {}
    suspend fun onWebsocketCloseEvent(code: Int, reason: String, remote: Boolean) {}
    suspend fun onWebsocketServerStartEvent() {}
    suspend fun onConnectEvent(event: ConnectEvent) {}
    suspend fun onHeartBeatEvent(event: HeartBeatEvent) {}
    suspend fun onMessage(rawMessage: String) {}
    suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {}
    suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage) {}
    suspend fun onBeAt(message: GroupMessage) {}
    suspend fun onBeRepliedInGroup(message: GroupMessage) {}
    suspend fun onBeRepliedInPrivate(message: PrivateMessage) {}
    suspend fun onGroupMessage(message: GroupMessage, json: String) {}
    suspend fun onPrivateMessage(message: PrivateMessage, json: String) {}
    suspend fun onInviteEvent(groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onApproveEvent(groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onLeaveEvent(groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onMemberKick(groupId: Long, operator: Long, time: Long) {}
    suspend fun onBeKicked(groupId: Long, operator: Long, time: Long) {}
    suspend fun onSetOperator(groupId: Long, operator: Long, time: Long) {}
    suspend fun onUnsetOperator(groupId: Long, operator: Long, time: Long) {}
    suspend fun onBan(groupId: Long, operator: Long, duration: Int, time: Long) {}
    suspend fun onPardon(groupId: Long, operator: Long, duration: Int, time: Long) {}
    suspend fun onJoinRequest(event: JoinGroupRequest) {}
    suspend fun onAddFriendRequest(event: AddFriendRequest) {}
    suspend fun onGroupFileUpload(event: FileEvent) {}
    suspend fun onPrivateFileUpload(event: FileEvent) {}
    suspend fun onPoke(event: PokeEvent) {}
}