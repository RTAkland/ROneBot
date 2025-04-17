/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/2
 */


package cn.rtast.rob.event.raw.onebot

import cn.rtast.rob.BotInstance
import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.actionable.BotStatusActionable
import cn.rtast.rob.onebot.OneBotAction

public data class RawBotOnlineEvent(
    val reason: String,
    var action: OneBotAction
) : BotStatusActionable {
    override suspend fun getBotUserId(): Long {
        return action.getLoginInfo().userId
    }

    override suspend fun getBotInstance(): BotInstance {
        return OneBotFactory.botManager.getBotInstanceByAction(action)
    }
}

public data class RawBotOfflineEvent(
    val tag: String,
    val message: String,
    var action: OneBotAction
) : BotStatusActionable {
    override suspend fun getBotUserId(): Long {
        return action.getLoginInfo().userId
    }

    override suspend fun getBotInstance(): BotInstance {
        return OneBotFactory.botManager.getBotInstanceByAction(action)
    }
}