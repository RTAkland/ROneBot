/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.event.raw.custom

import cn.rtast.rob.event.raw.group.RawBanEvent
import cn.rtast.rob.event.raw.group.RawBotBeKickEvent
import cn.rtast.rob.event.raw.group.RawGroupMemberLeaveEvent
import cn.rtast.rob.event.raw.group.RawJoinRequestApproveEvent
import cn.rtast.rob.event.raw.group.RawMemberBeInviteEvent
import cn.rtast.rob.event.raw.group.RawMemberKickEvent
import cn.rtast.rob.event.raw.group.RawPardonBanEvent
import cn.rtast.rob.event.raw.group.RawSetOperatorEvent
import cn.rtast.rob.event.raw.group.RawUnsetOperatorEvent
import cn.rtast.rob.event.raw.onebot.RawBotOfflineEvent
import cn.rtast.rob.event.raw.onebot.RawBotOnlineEvent
import cn.rtast.rob.event.raw.internal.RawWebsocketCloseEvent
import cn.rtast.rob.event.raw.internal.RawWebsocketErrorEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawBanEvent"))
public typealias RawBanEvent = RawBanEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawPardonBanEvent"))
public typealias RawPardonBanEvent = RawPardonBanEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.onebot.RawBotOnlineEvent"))
public typealias RawBotOnlineEvent = RawBotOnlineEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.onebot.RawBotOfflineEvent"))
public typealias RawBotOfflineEvent = RawBotOfflineEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawMemberBeInviteEvent"))
public typealias RawMemberBeInviteEvent = RawMemberBeInviteEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawJoinRequestApproveEvent"))
public typealias RawJoinRequestApproveEvent = RawJoinRequestApproveEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawGroupMemberLeaveEvent"))
public typealias RawGroupMemberLeaveEvent = RawGroupMemberLeaveEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawBotBeKickEvent"))
public typealias RawBotBeKickEvent = RawBotBeKickEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawMemberKickEvent"))
public typealias RawMemberKickEvent = RawMemberKickEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawSetOperatorEvent"))
public typealias RawSetOperatorEvent = RawSetOperatorEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawUnsetOperatorEvent"))
public typealias RawUnsetOperatorEvent = RawUnsetOperatorEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.internal.RawWebsocketCloseEvent"))
public typealias RawWebsocketCloseEvent = RawWebsocketCloseEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.internal.RawWebsocketErrorEvent"))
public typealias RawWebsocketErrorEvent = RawWebsocketErrorEvent

