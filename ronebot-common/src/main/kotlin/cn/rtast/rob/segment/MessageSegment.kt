/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage

/**
 * 非内部类, Segment作为超类
 */
sealed class Segment

/**
 * 纯文本
 */
data class Text(val text: String) : Segment()

/**
 * AT某人
 */
data class AT(val qq: Long) : Segment()

/**
 * 表情但是使用整形来构造
 */
data class Face(val id: Int) : Segment()

/**
 * 表情但是用[QQFace]来构造
 */
data class QFace(val id: QQFace) : Segment()

/**
 * 图片
 */
data class Image(val file: String, val base64: Boolean = false) : Segment()

/**
 * 语音
 */
data class Record(val file: String) : Segment()

/**
 * 视频
 */
data class Video(val file: String) : Segment()

/**
 * 戳一戳(旧版)
 */
data class Poke(val poke: PokeMessage) : Segment()

/**
 * 回复
 */
data class Reply(val id: Long) : Segment()

/**
 * XML消息
 */
data class XML(val xml: String) : Segment()

/**
 * 好友推荐
 */
data class FriendContact(val id: Long) : Segment()

/**
 * 群聊推荐
 */
data class GroupContact(val id: Long) : Segment()

/**
 * JSON消息
 */
data class JSON(val json: String) : Segment()

/**
 * 消息分享
 */
data class MusicShare(val type: MusicShareType, val id: String) : Segment()

/**
 * 插入换行符
 */
data class NewLine(val times: Int = 1) : Segment()

/**
 * 链接分享
 */
data class Share(
    val url: String,
    val title: String,
    val content: String? = null,
    val image: String? = null
) : Segment()

/**
 * 位置分享
 */
data class Location(
    val lat: Double,
    val lon: Double,
    val title: String? = null,
    val content: String? = null,
) : Segment()

/**
 * 自定义音乐分享
 */
data class CustomMusicShare(
    val url: String,
    val audio: String,
    val title: String,
    val content: String? = null,
    val image: String? = null,
    val type: String = "custom",
) : Segment()

/**
 * 剪刀石头布
 */
class Rps : Segment() {
    override fun toString() = "Rps"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rps) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * 骰子
 */
class Dice : Segment() {
    override fun toString() = "Dice"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dice) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * 屏震(旧版)
 */
class Shake : Segment() {
    override fun toString() = "Shake"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Shake) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}