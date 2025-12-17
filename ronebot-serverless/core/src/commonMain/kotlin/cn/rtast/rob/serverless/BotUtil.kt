/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 18:20
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.serverless

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration.Companion.seconds

public fun createBot(listener: OneBotListener): BotInstance = BotInstance(
    "", "", listener, false, 0, InstanceType.Client, "/", 0.seconds, 0.seconds,
    LogLevel.INFO
).apply {
    isServerless = true
    forwarderHost = "http://127.0.0.1:7071/test"
    sender = ServerlessSender()
}