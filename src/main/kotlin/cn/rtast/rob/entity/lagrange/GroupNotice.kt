/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */

@file:Suppress("unused")

package cn.rtast.rob.entity.lagrange

import com.google.gson.annotations.SerializedName

data class GroupNotice(
    val data: List<Data>
) {
    data class Data(
        @SerializedName("notice_id")
        val noticeId: String,
        @SerializedName("sender_id")
        val senderId: Long,
        @SerializedName("publish_time")
        val publishTime: Long,
        val message: Message
    )

    data class Message(
        val text: String,
        val images: List<Image>
    )

    data class Image(
        val id: String,
        val height: Int,
        val width: Int
    ) {
        // 根据ID来生成一个图片的URL地址
        val imageUrl = "https://p.qlogo.cn/gdynamic/$id/0/"
    }
}