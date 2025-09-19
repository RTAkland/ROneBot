/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:38 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment

import cn.rtast.rob.milky.enums.ImageSubType
import cn.rtast.rob.milky.util.json.ReceiveSegmentSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = ReceiveSegmentSerializer::class)
public sealed interface ReceiveSegment {
    public val type: ReceiveSegmentType
}

/**
 * 纯文本
 */
@SerialName("text")
@Serializable
public data class RTextSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Text,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        val text: String,
    )
}

/**
 * at类型
 */
@SerialName("mention")
@Serializable
public data class RMentionSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Mention,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("user_id")
        val userId: Long,
    )
}

/**
 * 表情
 */
@SerialName("face")
@Serializable
public data class RFaceSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Face,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("face_id")
        val faceId: String,
    )
}

/**
 * 回复
 */
@SerialName("reply")
@Serializable
public data class RReplySegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Reply,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("message_seq")
        val messageSeq: Long,
    )
}

/**
 * 图片
 */
@SerialName("image")
@Serializable
public data class RImageSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Image,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 资源 ID
         */
        @SerialName("resource_id")
        val resourceId: String,
        /**
         * 图片预览文本
         */
        val summary: String,
        /**
         * 图片类型
         */
        @SerialName("sub_type")
        val imageSubType: ImageSubType,
        /**
         * 图片宽度
         */
        val width: Int,
        /**
         * 图片高度
         */
        val height: Int,
        /**
         * 临时 URL
         */
        @SerialName("temp_url")
        val tempUrl: String,
    )
}

/**
 * 语音
 */
@SerialName("record")
@Serializable
public data class RRecordSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Record,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 资源 ID
         */
        @SerialName("resource_id")
        val resourceId: String,
        /**
         * 语音时长(秒)
         */
        val duration: Int,
        /**
         * 临时 URL
         */
        @SerialName("temp_url")
        val tempUrl: String,
    )
}

/**
 * 视频
 */
@SerialName("video")
@Serializable
public data class RVideoSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Video,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 资源ID
         */
        @SerialName("resource_id")
        val resourceId: String,
        /**
         * 临时 URL
         */
        @SerialName("temp_url")
        val tempUrl: String,
        /**
         * 视频宽度
         */
        val width: Int,
        /**
         * 视频高度
         */
        val height: Int,
        /**
         * 视频时长 (秒)
         */
        val duration: Int,
    )
}

/**
 * 合并转发
 */
@SerialName("forward")
@Serializable
public data class RForwardSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Forward,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 合并转发 ID
         */
        @SerialName("forward_id")
        val forwardId: String,
    )
}

/**
 * 市场表情
 */
@SerialName("market_face")
@Serializable
public data class RMarketFaceSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.MarketFace,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 市场表情 URL
         */
        val url: String,
    )
}

/**
 * 小程序
 */
@SerialName("light_app")
@Serializable
public data class RLightAppSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.LightApp,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 小程序名称
         */
        @SerialName("app_name")
        val appName: String,
        /**
         * 小程序 JSON 数据
         */
        @SerialName("json_payload")
        val jsonPayload: String,
    )
}

/**
 * XML
 */
@SerialName("xml")
@Serializable
public data class RXMLSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Xml,
) : ReceiveSegment {
    @Serializable
    public data class Data(
        /**
         * 服务 ID
         */
        @SerialName("service_id")
        val serviceId: Int,
        /**
         * XML 数据
         */
        @SerialName("xml_payload")
        val xmlPayload: String,
    )
}