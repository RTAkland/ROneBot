/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.metadata

import cn.rtast.rob.common.util.ExcludeFiled
import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName

data class HeartBeatEvent(
    @ExcludeFiled
    var action: OneBotAction,
    val interval: Int,
    val status: Status,
    val time: Long,
    @SerializedName("self_id")
    val selfId: Long,
    @SerializedName("post_type")
    val postType: String,
) {
    data class Status(
        @SerializedName("app_initialized")
        val appInitialized: Boolean,
        @SerializedName("app_enabled")
        val appEnabled: Boolean,
        @SerializedName("app_good")
        val appGood: Boolean,
        val online: Boolean,
        val good: Boolean,
        val memory: Long,
        @SerializedName("plugins_good")
        val pluginsGood: Boolean,
    )
}
