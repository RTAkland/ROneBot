/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:20 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws

import cn.rtast.rob.milky.enums.internal.MilkyEvents
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WebsocketEventStruct(
    @SerialName("event_type")
    val eventType: MilkyEvents,
    @SerialName("self_id")
    val selfId: Long
)