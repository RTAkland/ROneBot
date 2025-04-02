/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.event.raw.onebot

import cn.rtast.rob.enums.InboundMessageType
import cn.rtast.rob.enums.internal.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@Serializable
internal data class BaseEventMessage(
    @SerialName("meta_event_type")
    val metaEventType: MetaEventType?,
    @SerialName("sub_type")
    val subType: SubType?,
    @SerialName("message_type")
    val messageType: InboundMessageType?,
    @SerialName("post_type")
    val postType: PostType? = null,
    val time: Long? = null,
    @SerialName("notice_type")
    val noticeType: NoticeType?,
    @SerialName("request_type")
    val requestType: RequestType?,
    val echo: String? = null
)