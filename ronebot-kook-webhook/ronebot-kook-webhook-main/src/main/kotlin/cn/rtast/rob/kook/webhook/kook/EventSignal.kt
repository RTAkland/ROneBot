/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.kook

public enum class EventSignal(public val s: Int) {
    DispatchEventAndMessage(0), ServerHandshakeResponse(1),
    Heartbeat(2), HeartbeatResponse(3), Resume(4), ReconnectResponse(5),
    ResumeACK(6);

    public companion object {
        public fun fromCode(s: Int): EventSignal? = entries.find { it.s == s }
    }
}