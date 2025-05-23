/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:38 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment

import cn.rtast.rob.milky.enums.ImageSubType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed interface ReceiveSegment {
    public val type: ReceiveSegmentType
}

@SerialName("text")
@Serializable
public data class RTextSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Text
) : ReceiveSegment {
    @Serializable
    public data class Data(
        val text: String
    )
}

@SerialName("mention")
@Serializable
public data class RMentionSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Mention
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("user_id")
        val userId: Long
    )
}

@SerialName("face")
@Serializable
public data class RFaceSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Face
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("face_id")
        val faceId: String
    )
}

@SerialName("reply")
@Serializable
public data class RReplySegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Reply
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("message_seq")
        val messageSeq: Long
    )
}

@SerialName("image")
@Serializable
public data class RImageSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Image
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("resource_id")
        val resourceId: String,
        val summary: String?,
        @SerialName("sub_type")
        val imageSubType: ImageSubType
    )
}

@SerialName("record")
@Serializable
public data class RRecordSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Record
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("forward_id")
        val forwardId: String,
        /**
         * 秒
         */
        val duration: Int
    )
}

@SerialName("video")
@Serializable
public data class RVideoSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Video
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("resource_id")
        val resourceId: String
    )
}

@SerialName("forward")
@Serializable
public data class RForwardSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Forward
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("forward_id")
        val forwardId: String
    )
}

@SerialName("market_face")
@Serializable
public data class RMarketFaceSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.MarketFace
) : ReceiveSegment {
    @Serializable
    public data class Data(
        val url: String
    )
}

@SerialName("light_app")
@Serializable
public data class RLightAppSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.LightApp
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("app_name")
        val appName: String,
        @SerialName("json_payload")
        val jsonPayload: String
    )
}

@SerialName("xml")
@Serializable
public data class RXMLSegment(
    val data: Data,
    override val type: ReceiveSegmentType = ReceiveSegmentType.Xml
) : ReceiveSegment {
    @Serializable
    public data class Data(
        @SerialName("service_id")
        val serviceId: Int,
        @SerialName("xml_payload")
        val xmlPayload: String
    )
}