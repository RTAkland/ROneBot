/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 01:05
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob

import cn.rtast.cfworker.WorkerApplication
import cn.rtast.klogging.LogLevel
import cn.rtast.rob.OneBotFactory.Companion.botManager
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


public val workerApplication: WorkerApplication = WorkerApplication()

public suspend fun OneBotFactory.Companion.createWorkerBot(
    accessToken: String,
    listener: OneBotListener = object : OneBotListener {},
    path: String = "/",
    messageExecuteDuration: Duration = 0.seconds,
    logLevel: LogLevel = LogLevel.INFO
): BotInstance {
    val instance = BotInstance(
        "127.0.0.1", accessToken, listener,
        true, 1, InstanceType.Server,
        path, 0.seconds, messageExecuteDuration,
        logLevel
    ).createBot()
    botManager.addBotInstance(instance)
    return instance
}