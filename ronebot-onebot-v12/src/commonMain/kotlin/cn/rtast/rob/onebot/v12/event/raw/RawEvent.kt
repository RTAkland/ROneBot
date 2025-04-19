/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 08:06
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.event.raw

import cn.rtast.rob.onebot.v12.enums.internal.MessageType
import cn.rtast.rob.onebot.v12.enums.internal.MetaEventType
import cn.rtast.rob.onebot.v12.enums.internal.PostType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RawEvent(
    @SerialName("post_type")
    val postType: PostType,
    @SerialName("message_type")
    val messageType: MessageType? = null,
    @SerialName("meta_event_type")
    val metaEventType: MetaEventType? = null
)