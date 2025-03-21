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
public sealed class Segment {

    /**
     * 两个[Segment]追加
     */
    public operator fun plus(other: Segment): List<Segment> = listOf(this, other)

    /**
     * 一个[Segment]追加一个[Segment]列表
     */
    public operator fun plus(other: List<Segment>): List<Segment> = mutableListOf(this).apply { addAll(other) }

    /**
     * 追加String类型的数据
     */
    public operator fun plus(other: String): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Int类型的数据
     */
    public operator fun plus(other: Int): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Long类型的数据
     */
    public operator fun plus(other: Long): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Char类型的数据
     */
    public operator fun plus(other: Char): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Short类型的数据
     */
    public operator fun plus(other: Short): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Byte类型的数据
     */
    public operator fun plus(other: Byte): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Float类型的数据
     */
    public operator fun plus(other: Float): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Boolean类型的数据
     */
    public operator fun plus(other: Boolean): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 追加Double类型的数据
     */
    public operator fun plus(other: Double): List<Segment> = mutableListOf(this, Text(other))

    /**
     * 快速添加若干个重复的内容
     */
    public operator fun times(scale: Int): List<Segment> = List(scale) { this }
}

/**
 * 纯文本
 */
public data class Text(var text: Any) : Segment()

/**
 * AT某人
 */
public data class AT(var qq: Long) : Segment()

/**
 * AT某人
 */
public typealias Mention = AT

/**
 * 表情但是使用整形来构造
 */
public data class Face(var id: Int) : Segment()

/**
 * 表情但是用[QQFace]来构造
 */
public data class QFace(var id: QQFace) : Segment()

/**
 * 图片
 */
public data class Image(var resource: Resource, var base64: Boolean = false) : Segment()

/**
 * 语音
 */
public data class Record(var file: String) : Segment()

/**
 * 视频
 */
public data class Video(var file: String) : Segment()

/**
 * 戳一戳(旧版)
 */
public data class Poke(var poke: PokeMessage) : Segment()

/**
 * 回复
 */
public data class Reply(var id: Long) : Segment()

/**
 * XML消息
 */
public data class XML(var xml: String) : Segment()

/**
 * 好友推荐
 */
public data class FriendContact(var id: Long) : Segment()

/**
 * 群聊推荐
 */
public data class GroupContact(var id: Long) : Segment()

/**
 * JSON消息
 */
public data class JSON(var json: String) : Segment()

/**
 * 消息分享
 */
public data class MusicShare(var type: MusicShareType, var id: String) : Segment()

/**
 * 插入换行符
 */
public data class NewLine(var times: Int = 1) : Segment()

/**
 * 链接分享
 */
public data class Share(
    var url: String,
    var title: String,
    var content: String? = null,
    var image: String? = null
) : Segment()

/**
 * 位置分享
 */
public data class Location(
    var lat: Double,
    var lon: Double,
    var title: String? = null,
    var content: String? = null,
) : Segment()

/**
 * 自定义音乐分享
 */
public data class CustomMusicShare(
    var url: String,
    var audio: String,
    var title: String,
    var content: String? = null,
    var image: String? = null,
) : Segment()

/**
 * 空格
 */
public data class Spaces(var times: Int = 1) : Segment()

/**
 * 剪刀石头布
 */
public class Rps : Segment() {
    override fun toString(): String = "Rps"
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
public class Dice : Segment() {
    override fun toString(): String = "Dice"
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
public class Shake : Segment() {
    override fun toString(): String = "Shake"
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
public class AtAll : Segment() {
    override fun toString(): String = "AtAll"
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
public typealias MentionAll = AtAll