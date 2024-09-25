/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.ContactType

internal data class PlainText(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.text
) : BaseSegment {
    data class Data(val text: String)
}

internal data class Face(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.face
) : BaseSegment {
    data class Data(val id: String)
}

internal data class Image(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.image
) : BaseSegment {
    data class Data(val file: String)
}

internal data class Record(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.record
) : BaseSegment {
    data class Data(val file: String)
}

internal data class Video(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.video
) : BaseSegment {
    data class Data(val file: String)
}

internal data class AT(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.at
) : BaseSegment {
    data class Data(val qq: String)
}

internal data class Poke(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.poke
) : BaseSegment {
    data class Data(val type: String, val id: String)
}

internal data class Share(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.share
) : BaseSegment {
    data class Data(
        val url: String,
        val title: String,
        val content: String? = null,
        val image: String? = null
    )
}

internal data class Contact(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.contact
) : BaseSegment {
    data class Data(val type: ContactType, val id: String)
}

internal data class Location(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.location
) : BaseSegment {
    data class Data(
        val lat: String,
        val lon: String,
        val title: String? = null,
        val content: String? = null,
    )
}

internal data class MusicShare(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.music
) : BaseSegment {
    data class Data(val type: String, val id: String)
}

internal data class CustomMusicShare(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.music
) : BaseSegment {
    data class Data(
        val url: String,
        val audio: String,
        val title: String? = null,
        val content: String? = null,
        val image: String? = null,
        val type: String = "custom",
    )
}

internal data class Reply(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.reply
) : BaseSegment {
    data class Data(val id: String)
}

internal data class XML(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.xml
) : BaseSegment {
    data class Data(val data: String)
}

internal data class JSON(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.json
) : BaseSegment {
    data class Data(val data: String)
}

internal data class RPS(override val type: ArrayMessageType = ArrayMessageType.rps) : BaseSegment
internal data class DICE(override val type: ArrayMessageType = ArrayMessageType.dice) : BaseSegment
internal data class Shake(override val type: ArrayMessageType = ArrayMessageType.shake) : BaseSegment

internal data class Node(
    val data: Data,
    override val type: ArrayMessageType = ArrayMessageType.node
) : BaseSegment {
    data class Data(
        val name: String,
        val uin: String,
        val content: List<BaseSegment>
    )
}