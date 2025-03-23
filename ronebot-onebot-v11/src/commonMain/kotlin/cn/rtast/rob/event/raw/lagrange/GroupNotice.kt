/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */

@file:Suppress("unused")

package cn.rtast.rob.event.raw.lagrange

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GroupNotice(
    val data: List<GroupNotice>
) {
    @Serializable
    public data class GroupNotice(
        /**
         * 公告的ID
         */
        @SerialName("notice_id")
        val noticeId: String,
        /**
         * 发布者QQ号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 发布时间
         */
        @SerialName("publish_time")
        val publishTime: Long,
        /**
         * 公告内容
         */
        val message: Message
    )

    @Serializable
    public data class Message(
        /**
         * 公告文本
         */
        val text: String,
        /**
         * 公告内的图片
         */
        val images: List<Image>
    )

    @Serializable
    public data class Image(
        /**
         * 图片ID
         */
        val id: String,
        /**
         * 高
         */
        val height: Int,
        /**
         * 宽
         */
        val width: Int
    ) {
        /**
         * 根据ID来生成一个图片的URL地址
         */
        val imageUrl: String = "https://p.qlogo.cn/gdynamic/$id/0/"
    }
}