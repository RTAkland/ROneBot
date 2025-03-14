/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.kook

import cn.rtast.rob.common.http.SendActionExt
import cn.rtast.rob.kook.webhook.BotInstance

public class KookAction(
    internal val botInstance: BotInstance,
) : SendActionExt {
    override suspend fun send(api: String, payload: Any?): String {
        return ""
    }
}