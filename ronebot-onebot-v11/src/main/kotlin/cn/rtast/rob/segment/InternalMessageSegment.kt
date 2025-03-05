/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.enums.internal.ContactType
import cn.rtast.rob.onebot.MessageChain

/**
 * 内部使用的Segment作为超类
 */
internal interface InternalBaseSegment {
    val type: SegmentType
}

internal data class IPlainText(
    val data: Data,
    override val type: SegmentType = SegmentType.text
) : InternalBaseSegment {
    data class Data(val text: String)
}

internal data class IFace(
    val data: Data,
    override val type: SegmentType = SegmentType.face
) : InternalBaseSegment {
    data class Data(val id: String)
}

internal data class IImage(
    val data: Data,
    override val type: SegmentType = SegmentType.image
) : InternalBaseSegment {
    data class Data(val file: String)
}

internal data class IRecord(
    val data: Data,
    override val type: SegmentType = SegmentType.record
) : InternalBaseSegment {
    data class Data(val file: String)
}

internal data class IVideo(
    val data: Data,
    override val type: SegmentType = SegmentType.video
) : InternalBaseSegment {
    data class Data(val file: String)
}

internal data class IAT(
    val data: Data,
    override val type: SegmentType = SegmentType.at
) : InternalBaseSegment {
    data class Data(val qq: String)
}

internal data class IPoke(
    val data: Data,
    override val type: SegmentType = SegmentType.poke
) : InternalBaseSegment {
    data class Data(val type: String, val id: String)
}

internal data class IShare(
    val data: Data,
    override val type: SegmentType = SegmentType.share
) : InternalBaseSegment {
    data class Data(
        val url: String,
        val title: String,
        val content: String? = null,
        val image: String? = null
    )
}

internal data class IContact(
    val data: Data,
    override val type: SegmentType = SegmentType.contact
) : InternalBaseSegment {
    data class Data(val type: ContactType, val id: String)
}

internal data class ILocation(
    val data: Data,
    override val type: SegmentType = SegmentType.location
) : InternalBaseSegment {
    data class Data(
        val lat: String,
        val lon: String,
        val title: String? = null,
        val content: String? = null,
    )
}

internal data class IMusicShare(
    val data: Data,
    override val type: SegmentType = SegmentType.music
) : InternalBaseSegment {
    data class Data(val type: String, val id: String)
}

internal data class ICustomMusicShare(
    val data: Data,
    override val type: SegmentType = SegmentType.music
) : InternalBaseSegment {
    data class Data(
        val url: String,
        val audio: String,
        val title: String? = null,
        val content: String? = null,
        val image: String? = null,
        val type: String = "custom",
    )
}

internal data class IReply(
    val data: Data,
    override val type: SegmentType = SegmentType.reply
) : InternalBaseSegment {
    data class Data(val id: String)
}

internal data class IXml(
    val data: Data,
    override val type: SegmentType = SegmentType.xml
) : InternalBaseSegment {
    data class Data(val data: String)
}

internal data class IJson(
    val data: Data,
    override val type: SegmentType = SegmentType.json
) : InternalBaseSegment {
    data class Data(val data: String)
}

internal data class INode(
    val data: Data,
    override val type: SegmentType = SegmentType.node
) : InternalBaseSegment {
    data class Data(
        val name: String,
        val uin: String,
        val content: List<InternalBaseSegment>
    )
}

/**
 * Markdown 消息 ***WIP***
 */
internal data class IMarkdown(
    val data: Data,
    override val type: SegmentType = SegmentType.markdown
) : InternalBaseSegment {
    data class Data(val content: String)
}

internal data class IRps(override val type: SegmentType = SegmentType.rps) : InternalBaseSegment
internal data class IDice(override val type: SegmentType = SegmentType.dice) : InternalBaseSegment
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