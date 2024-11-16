/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import cn.rtast.rob.enums.ArrayMessageType
import com.google.gson.annotations.SerializedName

data class ArrayMessage(
    val type: ArrayMessageType,
    val data: Data
) {
    data class Data(
        val data: Any? = null,
        val id: Any? = null,
        val qq: String? = null,
        val text: String? = null,
        val file: String? = null,
        val filename: String? = null,
        val url: String? = null,
        val summary: String? = null,
        val subType: String? = null,
        val name: String? = null,
        val type: Any? = null,
        val title: String? = null,
        val lat: Double? = null,
        val lon: Double? = null,
        val content: String? = null,
        val audio: String? = null,
        @SerializedName("emoji_id")
        val emojiId: String? = null,
        @SerializedName("emoji_package_id")
        val emojiPackageId: String? = null,
        val key: String? = null,
        val large: String? = null
    ) {
        fun getJSON() = this.data.toString()
        fun getXML() = this.data.toString()
        fun getRecord() = MessageData.InboundRecord(this.file!!, this.url!!)
        fun getImage() =
            MessageData.InboundImage(this.file!!, this.filename!!, this.url!!, this.summary!!, this.subType!!)

        fun getFace() = MessageData.InboundFace(this.id.toString(), this.large!!)
        fun getAt() = MessageData.InboundAT(this.qq!!, this.name!!)
        fun getReply() = MessageData.InboundRecord(this.file!!, this.url!!)
        fun getMFace() =
            MessageData.InboundMFace(this.emojiId!!, this.emojiPackageId!!, this.key!!, this.url!!, this.summary!!)
    }
}

/**
 * 暂未使用的密封类有意向使用`is`操作符来判断
 * message的类型, 但是迁移起来有点麻烦所以就
 * 暂时不做了
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