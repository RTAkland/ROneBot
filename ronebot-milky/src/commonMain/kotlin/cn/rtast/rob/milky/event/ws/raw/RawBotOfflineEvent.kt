/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/1/25, 1:41 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Bot离线
 */
@Serializable
public data class RawBotOfflineEvent(
    val data: BotOffline,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class BotOffline(
        /**
         * 离线原因
         */
        val reason: String
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}