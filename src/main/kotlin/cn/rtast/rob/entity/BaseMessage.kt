/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.MetaEventType
import cn.rtast.rob.enums.NoticeType
import cn.rtast.rob.enums.PostType
import cn.rtast.rob.enums.RequestType
import cn.rtast.rob.enums.SubType
import com.google.gson.annotations.SerializedName

data class BaseMessage(
    @SerializedName("meta_event_type")
    val metaEventType: MetaEventType?,
    @SerializedName("sub_type")
    val subType: SubType,
    @SerializedName("message_type")
    val messageType: MessageType?,
    @SerializedName("raw_message")
    val rawMessage: String,
    @SerializedName("post_type")
    val postType: PostType,
    val time: Long,
    @SerializedName("notice_type")
    val noticeType: NoticeType?,
    @SerializedName("request_type")
    val requestType: RequestType?
)