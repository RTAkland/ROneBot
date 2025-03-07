/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.kook

import cn.rtast.rob.kook.webhook.event.RawMessageEvent

public interface KookListener {

    public suspend fun onChannelMessage(message: RawMessageEvent) {}
}