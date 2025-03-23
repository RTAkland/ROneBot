/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 删除群公告
 */
@Serializable
internal data class DeleteGroupNoticeApi(
    val action: String = "_del_group_notice",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("notice_id")
        val noticeId: String,
    )
}

/**
 * 获取群公告
 */
@Serializable
internal data class GetGroupNoticeApi(
    val action: String = "_get_group_notice",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
    )
}

/**
 * 发布群公告
 */
@Serializable
internal data class ReleaseGroupNoticeApi(
    val action: String = "_send_group_notice",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val content: String,
        val image: String,
    )
}