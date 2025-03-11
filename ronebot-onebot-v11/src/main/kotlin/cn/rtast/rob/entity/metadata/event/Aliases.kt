/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/9
 */

package cn.rtast.rob.entity.metadata.event

import cn.rtast.rob.event.raw.metadata.OneBotVersionInfo
import cn.rtast.rob.event.raw.metadata.RawConnectEvent
import cn.rtast.rob.event.raw.metadata.RawGroupNameChangeEvent
import cn.rtast.rob.event.raw.metadata.RawHeartBeatEvent

/**
 * 向下兼容而创建的类型别名
 */
@Deprecated("该包下的所有类名已被迁移请使用event包下的类")
public typealias OneBotVersionInfo = OneBotVersionInfo

/**
 * 向下兼容而创建的类型别名
 */
@Deprecated("该包下的所有类名已被迁移请使用event包下的类")
public typealias RawConnectEvent = RawConnectEvent

/**
 * 向下兼容而创建的类型别名
 */
@Deprecated("该包下的所有类名已被迁移请使用event包下的类")
public typealias RawGroupNameChangeEvent = RawGroupNameChangeEvent

/**
 * 向下兼容而创建的类型别名
 */
@Deprecated("该包下的所有类名已被迁移请使用event包下的类")
public typealias RawHeartBeatEvent = RawHeartBeatEvent
