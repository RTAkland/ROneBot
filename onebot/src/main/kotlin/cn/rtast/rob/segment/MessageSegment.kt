/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage
import cn.rtast.rob.enums.QQFace

data class Text(val text: String) : Segment
data class AT(val qq: Long) : Segment
data class Face(val id: Int) : Segment
data class QFace(val id: QQFace) : Segment
data class Image(val file: String, val base64: Boolean = false) : Segment
data class Record(val file: String) : Segment
data class Video(val file: String) : Segment
data class Poke(val poke: PokeMessage) : Segment
data class Reply(val id: Long) : Segment
data class XML(val xml: String) : Segment
data class FriendContact(val id: Long) : Segment
data class GroupContact(val id: Long) : Segment
data class JSON(val json: String) : Segment
data class MusicShare(val type: MusicShareType, val id: String) : Segment
class Rps : Segment
class Dice : Segment
class Shake : Segment
data class Share(
    val url: String,
    val title: String,
    val content: String? = null,
    val image: String? = null
) : Segment


data class Location(
    val lat: Double,
    val lon: Double,
    val title: String? = null,
    val content: String? = null,
) : Segment

data class CustomMusicShare(
    val url: String,
    val audio: String,
    val title: String,
    val content: String? = null,
    val image: String? = null,
    val type: String = "custom",
) : Segment