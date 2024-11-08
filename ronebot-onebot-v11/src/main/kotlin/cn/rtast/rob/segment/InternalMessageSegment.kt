/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.internal.ContactType

/**
 * 内部使用的Segment作为超类
 */
internal interface InternalBaseSegment {
    val type: ArrayMessageType
}

internal data class IPlainText(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.text
) : InternalBaseSegment {
    data class Data(val text: String)
}

internal data class IFace(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.face
) : InternalBaseSegment {
    data class Data(val id: String)
}

internal data class IImage(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.image
) : InternalBaseSegment {
    data class Data(val file: String)
}

internal data class IRecord(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.record
) : InternalBaseSegment {
    data class Data(val file: String)
}

internal data class IVideo(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.video
) : InternalBaseSegment {
    data class Data(val file: String)
}

internal data class IAT(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.at
) : InternalBaseSegment {
    data class Data(val qq: String)
}

internal data class IPoke(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.poke
) : InternalBaseSegment {
    data class Data(val type: String, val id: String)
}

internal data class IShare(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.share
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
    override val type: ArrayMessageType = ArrayMessageType.contact
) : InternalBaseSegment {
    data class Data(val type: ContactType, val id: String)
}

internal data class ILocation(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.location
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
    override val type: ArrayMessageType = ArrayMessageType.music
) : InternalBaseSegment {
    data class Data(val type: String, val id: String)
}

internal data class ICustomMusicShare(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.music
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
    override val type: ArrayMessageType = ArrayMessageType.reply
) : InternalBaseSegment {
    data class Data(val id: String)
}

internal data class IXml(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.xml
) : InternalBaseSegment {
    data class Data(val data: String)
}

internal data class IJson(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.json
) : InternalBaseSegment {
    data class Data(val data: String)
}

internal data class INode(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.node
) : InternalBaseSegment {
    data class Data(
        val name: String,
        val uin: String,
        val content: List<InternalBaseSegment>
    )
}

internal data class IRps(override val type: ArrayMessageType = ArrayMessageType.rps) : InternalBaseSegment
internal data class IDice(override val type: ArrayMessageType = ArrayMessageType.dice) : InternalBaseSegment
internal data class IShake(override val type: ArrayMessageType = ArrayMessageType.shake) : InternalBaseSegment
