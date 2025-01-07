/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import cn.rtast.rob.enums.SegmentType
import com.google.gson.annotations.SerializedName

data class ArrayMessage(
    val type: SegmentType,
    val data: Data
) {
    data class Data(
        /**
         * json/xml
         */
        val data: Any? = null,
        /**
         * ID
         */
        val id: Any? = null,
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
        val type: Any? = null,
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
        @SerializedName("emoji_id")
        val emojiId: String? = null,
        /**
         * emoji的包id
         */
        @SerializedName("emoji_package_id")
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
        @SerializedName("file_name")
        val fileName: String? = null,
        /**
         * 文件hash
         */
        @SerializedName("file_hash")
        val fileHash: String? = null,
        /**
         * 文件ID
         */
        @SerializedName("file_id")
        val fileId: String? = null,
    ) {
        /**
         * 获取json字符串
         */
        fun getJSON(): String? = this.data.toString()

        /**
         * 获取XML字符串
         */
        fun getXML(): String? = this.data.toString()

        /**
         * 获取语音
         */
        fun getRecord(): MessageData.InboundRecord? = MessageData.InboundRecord(this.file!!, this.url!!)

        /**
         * 获取表情
         */
        fun getFace(): MessageData.InboundFace? = MessageData.InboundFace(this.id.toString(), this.large!!)

        /**
         * 获取@
         */
        fun getAt(): MessageData.InboundAT? = MessageData.InboundAT(this.qq!!, this.name!!)

        /**
         * 获取回复
         */
        fun getReply(): MessageData.InboundRecord? = MessageData.InboundRecord(this.file!!, this.url!!)

        /**
         * 获取MFace (商城表情)
         */
        fun getMFace(): MessageData.InboundMFace? =
            MessageData.InboundMFace(this.emojiId!!, this.emojiPackageId!!, this.key!!, this.url!!, this.summary!!)

        /**
         * 获取图片
         */
        fun getImage(): MessageData.InboundImage? =
            MessageData.InboundImage(this.file!!, this.filename!!, this.url!!, this.summary!!, this.subType!!)
    }
}

/**
 * 暂未使用的密封类有意向使用`is`操作符来判断message的类型
 */
sealed class MessageData {
    data class InboundMFace(
        @SerializedName("emoji_id")
        val emojiId: String,
        @SerializedName("emoji_package_id")
        val emojiPackageId: String,
        val key: String,
        val url: String,
        val summary: String
    ) : MessageData()

    data class InboundImage(
        val file: String,
        val filename: String,
        val url: String,
        val summary: String,
        val subType: String
    ) : MessageData()

    data class InboundRecord(
        val file: String,
        val url: String
    ) : MessageData()

    data class InboundText(val text: String) : MessageData()
    data class InboundFace(val id: String, val large: String?) : MessageData()
    data class InboundJSON(val data: String) : MessageData()
    data class InboundXML(val data: String) : MessageData()
    data class InboundAT(val qq: String, val name: String) : MessageData()
    data class InboundReply(val id: Long) : MessageData()
}