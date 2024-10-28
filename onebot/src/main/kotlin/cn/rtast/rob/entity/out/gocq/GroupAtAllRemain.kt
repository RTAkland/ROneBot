/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.gocq

import com.google.gson.annotations.SerializedName

data class GroupAtAllRemain(
    val data: AtAllRemain
) {
    data class AtAllRemain(
        // 是否可以 @全体成员
        @SerializedName("can_at_all")
        val canAtAll: Boolean,
        // 群内所有管理当天剩余 @全体成员 次数
        @SerializedName("remain_at_all_count_for_group")
        val groupRemainCount: Int,
        // Bot 当天剩余 @全体成员 次数
        @SerializedName("remain_at_all_count_for_uin")
        val botRemainCount: Int,
    )
}