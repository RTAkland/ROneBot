/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 07:59
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.segment

import cn.rtast.rob.onebot.v12.enums.internal.SegmentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed interface InternalSegment {
    public val type: SegmentType
}

@Serializable
public data class InternalTextSegment(
    override val type: SegmentType = SegmentType.text,
    val data: Data
) : InternalSegment {
    @Serializable
    public data class Data(
        val text: String
    )
}

@Serializable
public data class InternalFileSegment(
    override val type: SegmentType = SegmentType.file,
    val data: Data
) : InternalSegment {
    @Serializable
    public data class Data(
        @SerialName("file_id")
        val fileId: String
    )
}

@Serializable
public data class InternalImageSegment(
    override val type: SegmentType = SegmentType.image,
    val data: Data
) : InternalSegment {
    @Serializable
    public data class Data(
        @SerialName("file_id")
        val fileId: String
    )
}

@Serializable
public data class InternalAtSegment(
    override val type: SegmentType,
    val data: Data
) : InternalSegment {
    @Serializable
    public data class Data(
        @SerialName("user_id")
        val userId: String,
        val qq: Long,
    )
}