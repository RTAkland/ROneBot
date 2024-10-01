/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity.out.lagrange

import cn.rtast.rob.enums.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class ReleaseGroupNoticeOut(
    val action: String = "_send_group_notice",
    val echo: MessageEchoType = MessageEchoType.ReleaseGroupNotice,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val content: String,
        val image: String,
    )
}