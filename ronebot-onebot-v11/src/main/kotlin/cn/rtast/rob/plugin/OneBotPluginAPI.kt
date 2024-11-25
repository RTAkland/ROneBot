/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/25
 */


package cn.rtast.rob.plugin

import cn.rtast.rob.BotInstance

class OneBotPluginAPI internal constructor(
    private val botInstances: List<BotInstance>
) : BasePluginAPI {

    val actions get() = botInstances.map { it to it.action }
}