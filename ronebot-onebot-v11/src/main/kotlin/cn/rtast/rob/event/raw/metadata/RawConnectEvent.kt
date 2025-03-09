/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.event.raw.metadata

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.onebot.OneBotAction
import com.google.gson.annotations.SerializedName

public data class RawConnectEvent(
    @ExcludeField
    var action: OneBotAction,
    @SerializedName("self_id")
    var selfId: String,
    val time: Long,
)