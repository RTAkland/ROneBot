/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:43
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.onebot12

import cn.rtast.rob.onebot.v12.event.packed.WebsocketClosedEvent
import cn.rtast.rob.onebot.v12.event.packed.WebsocketErrorEvent
import cn.rtast.rob.onebot.v12.event.packed.WebsocketOpenEvent
import cn.rtast.rob.onebot.v12.event.raw.message.RawGroupMessageEvent
import cn.rtast.rob.onebot.v12.event.raw.message.RawPrivateMessageEvent
import cn.rtast.rob.onebot.v12.event.raw.onebot.RawHeartbeatEvent

public interface OneBot12Listener {
    public suspend fun onWebsocketOpen(event: WebsocketOpenEvent) {}
    public suspend fun onWebsocketClosed(event: WebsocketClosedEvent) {}
    public suspend fun onWebsocketError(event: WebsocketErrorEvent) {}
    public suspend fun onRawMessage(message: String) {}
    public suspend fun onHeartbeat(event: RawHeartbeatEvent) {}
    public suspend fun onGroupMessage(event: RawGroupMessageEvent) {}
    public suspend fun onPrivateMessage(event: RawPrivateMessageEvent) {}
}