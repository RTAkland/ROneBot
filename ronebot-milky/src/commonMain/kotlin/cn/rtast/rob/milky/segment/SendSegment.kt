/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:09 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment

import cn.rtast.rob.milky.enums.ImageSubType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("_kind")
internal sealed interface SendSegment {
    val type: SendSegmentType
}

@Serializable
internal data class STextSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Text
) : SendSegment {
    @Serializable
    data class Data(
        @Serializable
        val text: String
    )
}

@Serializable
internal data class SAtSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.AT
) : SendSegment {
    @Serializable
    data class Data(
        @SerialName("user_id")
        val userId: Long
    )
}


@Serializable
internal data class SReplySegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Reply
) : SendSegment {
    @Serializable
    data class Data(
        @SerialName("message_seq")
        val messageSeq: Long
    )
}


@Serializable
internal data class SFaceSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Face
) : SendSegment {
    @Serializable
    data class Data(
        @SerialName("face_id")
        val faceId: String
    )
}


@Serializable
internal data class SImageSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Image
) : SendSegment {
    @Serializable
    data class Data(
        @SerialName("uri")
        val uri: String,
        @SerialName("sub_type")
        val subType: ImageSubType,
        val summary: String? = null
    )
}


@Serializable
internal data class SRecordSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Record
) : SendSegment {
    @Serializable
    data class Data(
        @SerialName("uri")
        val uri: String
    )
}


@Serializable
internal data class SVideoSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Video
) : SendSegment {
    @Serializable
    data class Data(
        @SerialName("uri")
        val uri: String,
        @SerialName("thumb_uri")
        val thumbUri: String? = null
    )
}


@Serializable
internal data class SForwardSegment(
    val data: Data,
    override val type: SendSegmentType = SendSegmentType.Forward
) : SendSegment {
    // TODO
    @Serializable
    data class Data(
        @SerialName("user_id")
        val userId: Long
    )
}