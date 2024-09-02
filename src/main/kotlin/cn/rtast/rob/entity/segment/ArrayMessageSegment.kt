/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.entity.segment

import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.ContactType

internal data class PlainText(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.text
) : BaseArrayMessage() {
    data class Data(val text: String)
}

internal data class Face(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.face
) : BaseArrayMessage() {
    data class Data(val id: String)
}

internal data class Image(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.image
) : BaseArrayMessage() {
    data class Data(val file: String)
}

internal data class Record(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.record
) : BaseArrayMessage() {
    data class Data(val file: String)
}

internal data class Video(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.video
) : BaseArrayMessage() {
    data class Data(val file: String)
}

internal data class AT(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.at
) : BaseArrayMessage() {
    data class Data(val qq: String)
}

internal data class Poke(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.poke
) : BaseArrayMessage() {
    data class Data(val type: String, val id: String)
}

internal data class Share(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.share
) : BaseArrayMessage() {
    data class Data(
        val url: String,
        val title: String,
        val content: String? = null,
        val image: String? = null
    )
}

internal data class Contact(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.contact
) : BaseArrayMessage() {
    data class Data(val type: ContactType, val id: String)
}

internal data class Location(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.location
) : BaseArrayMessage() {
    data class Data(
        val lat: String,
        val lon: String,
        val title: String? = null,
        val content: String? = null,
    )
}

internal data class MusicShare(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.music
) : BaseArrayMessage() {
    data class Data(val type: String, val id: String)
}

internal data class CustomMusicShare(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.music
) : BaseArrayMessage() {
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
    val type: ArrayMessageType = ArrayMessageType.reply
) : BaseArrayMessage() {
    data class Data(val id: String)
}

internal data class XML(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.xml
) : BaseArrayMessage() {
    data class Data(val data: String)
}

internal data class JSON(
    val data: Data,
    val type: ArrayMessageType = ArrayMessageType.json
) : BaseArrayMessage() {
    data class Data(val data: String)
}

internal data class RPS(val type: ArrayMessageType = ArrayMessageType.rps) : BaseArrayMessage()
internal data class DICE(val type: ArrayMessageType = ArrayMessageType.dice) : BaseArrayMessage()
internal data class Shake(val type: ArrayMessageType = ArrayMessageType.shake) : BaseArrayMessage()