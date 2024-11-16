/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.outbound.lagrange.set

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * 删除群公告
 */
internal data class DeleteGroupNoticeOut(
    val action: String = "_del_group_notice",
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("notice_id")
        val noticeId: String,
    )
}

/**
 * 获取群公告
 */
internal data class GetGroupNoticeOut(
    val action: String = "_get_group_notice",
    val echo: UUID,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}

/**
 * 发布群公告
 */
internal data class ReleaseGroupNoticeOut(
    val action: String = "_send_group_notice",
    val echo: UUID,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val content: String,
        val image: String,
    )
}