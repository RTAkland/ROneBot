/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/26
 */


package cn.rtast.rob.segment

sealed interface MessageSegment

/**
 * 纯文本
 */
data class TextSegment(
    // 文本内容
    val text: String
) : MessageSegment

/**
 * At消息
 */
data class AtSegment(
    // qq号
    val qq: Long,
    // qq昵称
    val name: String,
) : MessageSegment

/**
 * 骰子
 */
class DiceSegment : MessageSegment {
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * 表情
 */
data class FaceSegment(
    // 表情ID
    val id: Int,
    // 是否为大图
    val large: Boolean
) : MessageSegment

/**
 * 文件
 */
data class FileSegment(
    // 文件名
    val filename: String,
    // 文件哈希值
    val fileHash: String,
    // 文件ID
    val fileId: String,
    // 文件URL
    val url: String,
) : MessageSegment


/**
 * 合并转发
 */
data class ForwardSegment(
    // 合并转发消息ID
    val id: String
) : MessageSegment

/**
 * 图片
 */
data class ImageSegment(
    // 图片URL
    val file: String,
    // 图片名字
    val filename: String,
    // 和file一样是URL
    val url: String,
    // 图片备注(概括)
    val summary: String,
    // 子类型
    val subType: String
) : MessageSegment

/**
 * JSON卡片
 */
data class JsonSegment(
    // json内容
    val data: String
) : MessageSegment

/**
 * 位置分享
 */
data class LocationSegment(
    // 纬度
    val lat: String,
    // 经度
    val lon: String,
    // 地点名称
    val title: String,
    val content: String
) : MessageSegment

/**
 * Markdown消息
 */
data class MarkdownSegment(
    // markdown内容
    val content: String,
) : MessageSegment

class RpsSegment : MessageSegment {
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

/**
 * Xml卡片
 */
data class XmlSegment(
    // xml内容
    val data: String
) : MessageSegment

/**
 * 音乐分享
 */
data class MusicSegment(
    // 歌曲平台
    val type: String,
    // 歌曲ID
    val id: String,
    // 歌曲详情URL
    val url: String,
    // 歌曲名
    val title: String,
    // 歌曲音频链接
    val audio: String,
    // 歌曲内容
    val content: String,
    // 歌曲封面
    val image: String,
) : MessageSegment

/**
 * 商城表情
 */
data class MFaceSegment(
    // 商城表情图片URL
    val url: String,
    // 商城表情包ID
    val emojiPackageId: String,
    // 商城表情ID
    val emojiId: String,
    // 商城表情Key
    val key: String,
    // 商城表情备注
    val summary: String
) : MessageSegment

/**
 * 语音
 */
data class RecordSegment(
    // 音频地址
    val file: String,
    // 音频地址
    val url: String,
) : MessageSegment

/**
 * 回复
 */
data class ReplySegment(
    // 被回复的消息ID
    val id: String,
) : MessageSegment