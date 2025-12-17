/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 18:26
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.serverless

import cn.rtast.rob.BaseBotInstance

public interface ServerlessWebsocketClient<in T : BaseBotInstance> {
    public suspend fun serverlessSend(message: String, bot: T)
}