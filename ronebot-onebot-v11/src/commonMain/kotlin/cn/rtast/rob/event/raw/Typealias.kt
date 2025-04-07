/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

@file:Suppress("DEPRECATION")

package cn.rtast.rob.event.raw

import cn.rtast.rob.event.raw.file.GetFileResponse
import cn.rtast.rob.event.raw.file.GroupFileSystemInfo
import cn.rtast.rob.event.raw.file.UploadGroupFileResponse
import cn.rtast.rob.event.raw.file.UploadPrivateFileResponse
import cn.rtast.rob.event.raw.friend.ArkSharePeerFriendResponse
import cn.rtast.rob.event.raw.friend.ArkSharePeerResponse
import cn.rtast.rob.event.raw.friend.FriendList
import cn.rtast.rob.event.raw.friend.GetFriendWithCategory
import cn.rtast.rob.event.raw.group.ArkShareGroupResponse
import cn.rtast.rob.event.raw.group.GroupAtAllRemain
import cn.rtast.rob.event.raw.group.GroupIgnoreAddRequest
import cn.rtast.rob.event.raw.group.GroupInfo
import cn.rtast.rob.event.raw.group.GroupList
import cn.rtast.rob.event.raw.group.GroupMemberInfo
import cn.rtast.rob.event.raw.group.GroupMemberList
import cn.rtast.rob.event.raw.group.GroupShutListResponse
import cn.rtast.rob.event.raw.group.ReactionEvent
import cn.rtast.rob.event.raw.info.GetProfileLike
import cn.rtast.rob.event.raw.info.LoginInfo
import cn.rtast.rob.event.raw.info.OCRImage
import cn.rtast.rob.event.raw.info.RobotUinRange
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.event.raw.message.ArrayMessage
import cn.rtast.rob.event.raw.message.BaseMessage
import cn.rtast.rob.event.raw.message.GetMessage
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.message.RawGroupRevokeMessage
import cn.rtast.rob.event.raw.message.RawPrivateRevokeMessage
import cn.rtast.rob.event.raw.request.AddFriendRequestEvent
import cn.rtast.rob.event.raw.request.JoinGroupRequestEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.file.GetFileResponse"))
public typealias GetFileResponse = GetFileResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.file.GroupFileSystemInfo"))
public typealias GroupFileSystemInfo = GroupFileSystemInfo

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.file.UploadGroupFileResponse"))
public typealias UploadGroupFileResponse = UploadGroupFileResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.file.UploadPrivateFileResponse"))
public typealias UploadPrivateFileResponse = UploadPrivateFileResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.friend.ArkSharePeerFriendResponse"))
public typealias ArkSharePeerFriendResponse = ArkSharePeerFriendResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.friend.ArkSharePeerResponse"))
public typealias ArkSharePeerResponse = ArkSharePeerResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.friend.FriendList"))
public typealias FriendList = FriendList

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.friend.GetFriendWithCategory"))
public typealias GetFriendWithCategory = GetFriendWithCategory

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.ArkShareGroupResponse"))
public typealias ArkShareGroupResponse = ArkShareGroupResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupAtAllRemain"))
public typealias GroupAtAllRemain = GroupAtAllRemain

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupIgnoreAddRequest"))
public typealias GroupIgnoreAddRequest = GroupIgnoreAddRequest

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupInfo"))
public typealias GroupInfo = GroupInfo

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupList"))
public typealias GroupList = GroupList

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupMemberInfo"))
public typealias GroupMemberInfo = GroupMemberInfo

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupMemberList"))
public typealias GroupMemberList = GroupMemberList

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.GroupShutListResponse"))
public typealias GroupShutListResponse = GroupShutListResponse

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.ReactionEvent"))
public typealias ReactionEvent = ReactionEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.info.GetProfileLike"))
public typealias GetProfileLike = GetProfileLike

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.info.LoginInfo"))
public typealias LoginInfo = LoginInfo

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.info.OCRImage"))
public typealias OCRImage = OCRImage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.info.RobotUinRange"))
public typealias RobotUinRange = RobotUinRange

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.info.StrangerInfo"))
public typealias StrangerInfo = StrangerInfo

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.ArrayMessage"))
public typealias ArrayMessage = ArrayMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.BaseMessage"))
public typealias BaseMessage = BaseMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.GetMessage"))
public typealias GetMessage = GetMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.GroupMessage"))
public typealias GroupMessage = GroupMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.PrivateMessage"))
public typealias PrivateMessage = PrivateMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.RawGroupRevokeMessage"))
public typealias RawGroupRevokeMessage = RawGroupRevokeMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.message.RawPrivateRevokeMessage"))
public typealias RawPrivateRevokeMessage = RawPrivateRevokeMessage

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.request.AddFriendRequestEvent"))
public typealias AddFriendRequestEvent = AddFriendRequestEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.request.JoinGroupRequestEvent"))
public typealias JoinGroupRequestEvent = JoinGroupRequestEvent
