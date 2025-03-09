/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/9
 */

package cn.rtast.rob.entity.custom

import cn.rtast.rob.event.raw.custom.RawBanEvent
import cn.rtast.rob.event.raw.custom.RawBotBeKickEvent
import cn.rtast.rob.event.raw.custom.RawBotOfflineEvent
import cn.rtast.rob.event.raw.custom.RawBotOnlineEvent
import cn.rtast.rob.event.raw.custom.RawGroupMemberLeaveEvent
import cn.rtast.rob.event.raw.custom.RawJoinRequestApproveEvent
import cn.rtast.rob.event.raw.custom.RawMemberBeInviteEvent
import cn.rtast.rob.event.raw.custom.RawMemberKickEvent
import cn.rtast.rob.event.raw.custom.RawPardonBanEvent
import cn.rtast.rob.event.raw.custom.RawSetOperatorEvent
import cn.rtast.rob.event.raw.custom.RawUnsetOperatorEvent
import cn.rtast.rob.event.raw.custom.RawWebsocketCloseEvent
import cn.rtast.rob.event.raw.custom.RawWebsocketErrorEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawBanEvent = RawBanEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawPardonBanEvent = RawPardonBanEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawBotOnlineEvent = RawBotOnlineEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawBotOfflineEvent = RawBotOfflineEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawMemberBeInviteEvent = RawMemberBeInviteEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawJoinRequestApproveEvent = RawJoinRequestApproveEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawGroupMemberLeaveEvent = RawGroupMemberLeaveEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawBotBeKickEvent = RawBotBeKickEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawMemberKickEvent = RawMemberKickEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawSetOperatorEvent = RawSetOperatorEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawUnsetOperatorEvent = RawUnsetOperatorEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawWebsocketCloseEvent = RawWebsocketCloseEvent

/**
 * 向下兼容而创建的类型别名
 */
public typealias RawWebsocketErrorEvent = RawWebsocketErrorEvent
