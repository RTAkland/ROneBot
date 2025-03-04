/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.segment

import cn.rtast.rob.entity.Resource
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage
import cn.rtast.rob.enums.QQFace

/**
 * 非内部类, Segment作为超类
 */
sealed class Segment {

    /**
     * 两个[Segment]追加
     */
    operator fun plus(other: Segment): List<Segment> = listOf(this, other)

    /**
     * 一个[Segment]追加一个[Segment]列表
     */
    operator fun plus(other: List<Segment>): List<Segment> = mutableListOf(this).apply { addAll(other) }

    /**
     * 追加String类型的数据
     */
    operator fun plus(other: String): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Int类型的数据
     */
    operator fun plus(other: Int): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Long类型的数据
     */
    operator fun plus(other: Long): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Char类型的数据
     */
    operator fun plus(other: Char): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Short类型的数据
     */
    operator fun plus(other: Short): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Byte类型的数据
     */
    operator fun plus(other: Byte): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Float类型的数据
     */
    operator fun plus(other: Float): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Boolean类型的数据
     */
    operator fun plus(other: Boolean): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Double类型的数据
     */
    operator fun plus(other: Double): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 快速添加若干个重复的内容
     */
    operator fun times(scale: Int): List<Segment> = List(scale) { this }
}

/**
 * 纯文本
 */
data class Text(var text: Any) : Segment()

/**
 * AT某人
 */
data class AT(var qq: Long) : Segment()

/**
 * AT某人
 */
typealias Mention = AT

/**
 * 表情但是使用整形来构造
 */
data class Face(var id: Int) : Segment()

/**
 * 表情但是用[QQFace]来构造
 */
data class QFace(var id: QQFace) : Segment()

/**
 * 图片
 */
data class Image(var resource: Resource, var base64: Boolean = false) : Segment()

/**
 * 语音
 */
data class Record(var file: String) : Segment()

/**
 * 视频
 */
data class Video(var file: String) : Segment()

/**
 * 戳一戳(旧版)
 */
data class Poke(var poke: PokeMessage) : Segment()

/**
 * 回复
 */
data class Reply(var id: Long) : Segment()

/**
 * XML消息
 */
data class XML(var xml: String) : Segment()

/**
 * 好友推荐
 */
data class FriendContact(var id: Long) : Segment()

/**
 * 群聊推荐
 */
data class GroupContact(var id: Long) : Segment()

/**
 * JSON消息
 */
data class JSON(var json: String) : Segment()

/**
 * 消息分享
 */
data class MusicShare(var type: MusicShareType, var id: String) : Segment()

/**
 * 插入换行符
 */
data class NewLine(var times: Int = 1) : Segment()

/**
 * 链接分享
 */
data class Share(
    var url: String,
    var title: String,
    var content: String? = null,
    var image: String? = null
) : Segment()

/**
 * 位置分享
 */
data class Location(
    var lat: Double,
    var lon: Double,
    var title: String? = null,
    var content: String? = null,
) : Segment()

/**
 * 自定义音乐分享
 */
data class CustomMusicShare(
    var url: String,
    var audio: String,
    var title: String,
    var content: String? = null,
    var image: String? = null,
) : Segment()

/**
 * 空格
 */
data class Spaces(var times: Int = 1) : Segment()

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

/**
 * AT全体成员
 */
class AtAll : Segment() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * AT全体成员
 */
typealias MentionAll = AtAll