/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.enums.internal.ContactType
import cn.rtast.rob.onebot.MessageChain
import kotlinx.serialization.Serializable

/**
 * 用于反序列化OneBot下发的消息段的一系列数据类
 */
@Serializable
internal sealed interface InternalBaseSegment {
    val type: SegmentType
}

@Serializable
internal data class IPlainText(
    val data: Data,
    override val type: SegmentType = SegmentType.text
) : InternalBaseSegment {
    @Serializable
    data class Data(val text: String)
}

@Serializable
internal data class IFace(
    val data: Data,
    override val type: SegmentType = SegmentType.face
) : InternalBaseSegment {
    @Serializable
    data class Data(val id: String)
}

@Serializable
internal data class IImage(
    val data: Data,
    override val type: SegmentType = SegmentType.image
) : InternalBaseSegment {
    @Serializable
    data class Data(val file: String)
}

@Serializable
internal data class IRecord(
    val data: Data,
    override val type: SegmentType = SegmentType.record
) : InternalBaseSegment {
    @Serializable
    data class Data(val file: String)
}

@Serializable
internal data class IVideo(
    val data: Data,
    override val type: SegmentType = SegmentType.video
) : InternalBaseSegment {
    @Serializable
    data class Data(val file: String)
}

@Serializable
internal data class IAT(
    val data: Data,
    override val type: SegmentType = SegmentType.at
) : InternalBaseSegment {
    @Serializable
    data class Data(val qq: String)
}

@Serializable
internal data class IPoke(
    val data: Data,
    override val type: SegmentType = SegmentType.poke
) : InternalBaseSegment {
    @Serializable
    data class Data(val type: String, val id: String)
}

@Serializable
internal data class IShare(
    val data: Data,
    override val type: SegmentType = SegmentType.share
) : InternalBaseSegment {
    @Serializable
    data class Data(
        val url: String,
        val title: String,
        val content: String? = null,
        val image: String? = null
    )
}

@Serializable
internal data class IContact(
    val data: Data,
    override val type: SegmentType = SegmentType.contact
) : InternalBaseSegment {
    @Serializable
    data class Data(val type: ContactType, val id: String)
}

@Serializable
internal data class ILocation(
    val data: Data,
    override val type: SegmentType = SegmentType.location
) : InternalBaseSegment {
    @Serializable
    data class Data(
        val lat: String,
        val lon: String,
        val title: String? = null,
        val content: String? = null,
    )
}

@Serializable
internal data class IMusicShare(
    val data: Data,
    override val type: SegmentType = SegmentType.music
) : InternalBaseSegment {
    @Serializable
    data class Data(val type: String, val id: String)
}

@Serializable
internal data class ICustomMusicShare(
    val data: Data,
    override val type: SegmentType = SegmentType.music
) : InternalBaseSegment {
    @Serializable
    data class Data(
        val url: String,
        val audio: String,
        val title: String? = null,
        val content: String? = null,
        val image: String? = null,
        val type: String = "custom",
    )
}

@Serializable
internal data class IReply(
    val data: Data,
    override val type: SegmentType = SegmentType.reply
) : InternalBaseSegment {
    @Serializable
    data class Data(val id: String)
}

@Serializable
internal data class IXml(
    val data: Data,
    override val type: SegmentType = SegmentType.xml
) : InternalBaseSegment {
    @Serializable
    data class Data(val data: String)
}

@Serializable
internal data class IJson(
    val data: Data,
    override val type: SegmentType = SegmentType.json
) : InternalBaseSegment {
    @Serializable
    data class Data(val data: String)
}

@Serializable
internal data class INode(
    val data: Data,
    override val type: SegmentType = SegmentType.node
) : InternalBaseSegment {
    @Serializable
    data class Data(
        val name: String,
        val uin: String,
        val content: List<InternalBaseSegment>
    )
}

@Serializable
internal data class IRps(override val type: SegmentType = SegmentType.rps) : InternalBaseSegment

@Serializable
internal data class IDice(override val type: SegmentType = SegmentType.dice) : InternalBaseSegment

@Serializable
internal data class IShake(override val type: SegmentType = SegmentType.shake) : InternalBaseSegment

/**
 * 内部使用
 * 将一个[Collection] [InternalBaseSegment] 转换成[MessageChain]
 */
internal fun Collection<InternalBaseSegment>.toMessageChainInternal() = this.toMessageChainBuilderInternal().build()

/**
 * 内部使用
 * 将一个[Collection] [InternalBaseSegment] 转换成[MessageChain.Builder]
 */
internal fun Collection<InternalBaseSegment>.toMessageChainBuilderInternal(): MessageChain.Builder {
    val builder = MessageChain.Builder()
    builder.addRawArrayMessage(this as List)
    return builder
}