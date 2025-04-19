/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 07:44
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.event.raw.onebot

import cn.rtast.rob.onebot.v12.enums.internal.MetaEventType
import cn.rtast.rob.onebot.v12.enums.internal.PostType
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Action
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawHeartbeatEvent(
    val time: Long,
    val interval: Int,
    @SerialName("self_id")
    val selfId: Long,
    @SerialName("post_type")
    val postType: PostType,
    @SerialName("meta_event_type")
    val metaEventType: MetaEventType,
    val status: Status
) {
    @Serializable
    public data class Status(
        val online: Boolean,
        val good: Boolean
    )

    @Transient
    public lateinit var action: OneBot12Action
}
