/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/31
 */


package cn.rtast.rob.entity.metadata

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName


data class GroupNameChangeEvent(
    @ExcludeField
    var action: OneBotAction,
    val groupId: Long,
    @SerializedName("self_id")
    val selfId: Long,
    val name: String,
)