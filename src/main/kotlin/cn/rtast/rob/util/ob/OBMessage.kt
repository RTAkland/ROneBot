/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.*

interface OBMessage : OBAction {
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
    suspend fun onJoinRequest(groupId: Long, userId: Long, comment: String, time: Long) {}
    suspend fun onGetGroupMemberListResponse(members: GroupMemberList) {}
    suspend fun onGetOneBotVersionInfoResponse(info: OneBotVersionInfo) {}
    suspend fun onGetGroupMemberInfoResponse(info: GroupMemberInfo) {}
    suspend fun onGetGroupListResponse(groupList: GroupList) {}
    suspend fun onGetFriendListResponse(friendList: FriendList) {}
    suspend fun onGetStrangerInfoResponse(info: StrangerInfo) {}
    suspend fun onGetLoginInfoResponse(info: LoginInfo) {}
    suspend fun onCanSendImageResponse(result: Boolean) {}
    suspend fun onCanSendRecordResponse(result: Boolean) {}
    suspend fun onGetGroupMessageResponse(message: GetMessage) {}
    suspend fun onGetPrivateMessageResponse(message: GetMessage) {}
    suspend fun onGetForwardMessageResponse(messageJson: String) {}
    suspend fun onGetGroupInfoResponse(groupInfo: GroupInfo) {}
    suspend fun onGroupFileUpload(groupId: Long, userId: Long, file: FileEvent) {}
    suspend fun onPrivateFileUpload(userId: Long, file: FileEvent) {}
}