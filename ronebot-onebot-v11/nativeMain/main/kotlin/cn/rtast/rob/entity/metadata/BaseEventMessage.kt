/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.metadata

import cn.rtast.rob.enums.InboundMessageType
import cn.rtast.rob.enums.internal.*
import com.google.gson.annotations.SerializedName
import java.util.*

internal data class BaseEventMessage(
    @SerializedName("meta_event_type")
    val metaEventType: MetaEventType?,
    @SerializedName("sub_type")
    val subType: SubType?,
    @SerializedName("message_type")
    val messageType: InboundMessageType?,
    @SerializedName("raw_message")
    val rawMessage: String,
    @SerializedName("post_type")
    val postType: PostType,
    val time: Long,
    @SerializedName("notice_type")
    val noticeType: NoticeType?,
    @SerializedName("request_type")
    val requestType: RequestType?,
    val echo: UUID?
)