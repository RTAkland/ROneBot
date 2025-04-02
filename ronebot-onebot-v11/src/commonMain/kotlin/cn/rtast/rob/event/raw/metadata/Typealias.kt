/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.event.raw.metadata

import cn.rtast.rob.event.raw.group.RawGroupNameChangeEvent
import cn.rtast.rob.event.raw.onebot.OneBotVersionInfo
import cn.rtast.rob.event.raw.onebot.RawConnectEvent
import cn.rtast.rob.event.raw.onebot.RawHeartBeatEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.onebot.OneBotVersionInfo"))
public typealias OneBotVersionInfo = OneBotVersionInfo

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.onebot.RawConnectEvent"))
public typealias RawConnectEvent = RawConnectEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.group.RawGroupNameChangeEvent"))
public typealias RawGroupNameChangeEvent = RawGroupNameChangeEvent

@Deprecated("请替换为新的包名", replaceWith = ReplaceWith("cn.rtast.rob.event.raw.onebot.RawHeartBeatEvent"))
public typealias RawHeartBeatEvent = RawHeartBeatEvent

