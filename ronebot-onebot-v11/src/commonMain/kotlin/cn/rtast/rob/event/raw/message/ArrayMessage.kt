/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.event.raw.message

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.segment.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
public data class ArrayMessage(
    val type: SegmentType,
    val data: Data
) {
    @Serializable
    public data class Data(
        /**
         * json/xml
         */
        val data: String? = null,
        /**
         * ID
         */
        val id: String? = null,
        /**
         * qq号
         */
        val qq: String? = null,
        /**
         * 纯文本消息的内容
         */
        val text: String? = null,
        /**
         * 文件id
         */
        val file: String? = null,
        /**
         * 文件名
         */
        val filename: String? = null,
        /**
         * 文件/图片/语音的url
         */
        val url: String? = null,
        /**
         * 大纲
         */
        val summary: String? = null,
        /**
         * 子类型/内部使用
         */
        val subType: String? = null,
        /**
         * 名称/通用
         */
        val name: String? = null,
        val type: String? = null,
        /**
         * 标题/通用
         */
        val title: String? = null,
        /**
         * 纬度
         */
        val lat: Double? = null,
        /**
         * 经度
         */
        val lon: Double? = null,
        /**
         * 内容/通用
         */
        val content: String? = null,
        /**
         * 音频链接
         */
        val audio: String? = null,
        /**
         * emoji的id
         */
        @SerialName("emoji_id")
        val emojiId: String? = null,
        /**
         * emoji的包id
         */
        @SerialName("emoji_package_id")
        val emojiPackageId: String? = null,
        /**
         *
         */
        val key: String? = null,
        /**
         * 图片是否为large
         */
        val large: String? = null,
        /**
         * 文件名
         */
        @SerialName("file_name")
        val fileName: String? = null,
        /**
         * 文件hash
         */
        @SerialName("file_hash")
        val fileHash: String? = null,
        /**
         * 文件ID
         */
        @SerialName("file_id")
        val fileId: String? = null,
    )
}

/**
 * 将大多数公共字段细分成某种特殊数据类
 */
public fun List<ArrayMessage>.toSegments(): List<MessageSegment> = this.serialize()

/**
 * 将大多数公共字段细分成某种特殊数据类
 */
public fun List<ArrayMessage>.serialize(): List<MessageSegment> {
    val segments = mutableListOf<MessageSegment?>()
    this.forEach {
        segments.add(
            when (it.type) {
                SegmentType.text -> TextSegment(it.data.text!!)
                SegmentType.image -> ImageSegment(
                    it.data.file!!,
                    it.data.filename!!,
                    it.data.url!!,
                    it.data.summary!!,
                    it.data.subType!!
                )

                SegmentType.face -> FaceSegment(it.data.id?.toInt()!!, it.data.large?.toBoolean() == true)
                SegmentType.record -> RecordSegment(it.data.file!!, it.data.url!!)
                SegmentType.at -> AtSegment(it.data.qq!!.toLong(), it.data.name!!)
                SegmentType.rps -> RpsSegment()
                SegmentType.shake -> null
                SegmentType.poke -> null
                SegmentType.share -> null
                SegmentType.reply -> ReplySegment(it.data.id.toString())
                SegmentType.json -> JsonSegment(it.data.data.toString())
                SegmentType.forward -> ForwardSegment(it.data.id.toString())
                SegmentType.dice -> DiceSegment()
                SegmentType.video -> null
                SegmentType.contact -> null
                SegmentType.location -> LocationSegment(
                    it.data.lat.toString(),
                    it.data.lon.toString(),
                    it.data.title!!,
                    it.data.content!!
                )

                SegmentType.music -> null
                SegmentType.xml -> XmlSegment(it.data.data.toString())
                SegmentType.mface -> MFaceSegment(
                    it.data.url!!,
                    it.data.emojiPackageId!!,
                    it.data.emojiId!!,
                    it.data.key!!,
                    it.data.summary!!
                )

                SegmentType.node -> null
                SegmentType.file -> FileSegment(it.data.fileName!!, it.data.fileHash!!, it.data.fileId!!, it.data.url!!)
                SegmentType.markdown -> null
            }
        )
    }
    return segments.mapNotNull { it }
}

/**
 * 判断是否存在某种Segment
 */
public fun List<MessageSegment>.has(clazz: KClass<out MessageSegment>): Boolean {
    return this.any { clazz.isInstance(it) }
}

/**
 * 判断是否存在某种Segment
 */
public fun hasSegment(segments: List<MessageSegment>, clazz: KClass<out MessageSegment>): Boolean =
    segments.has(clazz)