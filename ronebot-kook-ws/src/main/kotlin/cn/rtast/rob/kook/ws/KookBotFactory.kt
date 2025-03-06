/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.ws

import cn.rtast.rob.BotFactory

public object KookBotFactory : BotFactory {
    override var totalCommandExecutionTimes: Int = 0
    override var privateCommandExecutionTimes: Int = 0
    override var groupCommandExecutionTimes: Int = 0

}