/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity.out.lagrange

import com.google.gson.annotations.SerializedName

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