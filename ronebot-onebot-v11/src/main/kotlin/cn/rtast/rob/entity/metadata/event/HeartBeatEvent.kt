/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.metadata.event

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.onebot.OneBotAction
import com.google.gson.annotations.SerializedName

data class HeartBeatEvent(
    @ExcludeField
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
        /**
         * OneBot实现是否已初始化
         */
        @SerializedName("app_initialized")
        val appInitialized: Boolean,
        /**
         * OneBot实现是否已开启
         */
        @SerializedName("app_enabled")
        val appEnabled: Boolean,
        /**
         * OneBot实现是否正常工作
         */
        @SerializedName("app_good")
        val appGood: Boolean,
        /**
         * Bot是否在线
         */
        val online: Boolean,
        /**
         * OneBot实现是否状态良好
         */
        val good: Boolean,
        /**
         * OneBot实现占用内存
         */
        val memory: Long,
        /**
         * OneBot实现插件是否工作良好
         */
        @SerializedName("plugins_good")
        val pluginsGood: Boolean,
    )
}
