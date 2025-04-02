/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.event.raw.onebot

import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawHeartBeatEvent(
    val interval: Int,
    val status: Status,
    val time: Long,
    @SerialName("self_id")
    val selfId: Long,
    @SerialName("post_type")
    val postType: String,
) {

    @Transient
    lateinit var action: OneBotAction

    @Serializable
    public data class Status(
        /**
         * OneBot实现是否已初始化
         */
        @SerialName("app_initialized")
        val appInitialized: Boolean = true,
        /**
         * OneBot实现是否已开启
         */
        @SerialName("app_enabled")
        val appEnabled: Boolean = true,
        /**
         * OneBot实现是否正常工作
         */
        @SerialName("app_good")
        val appGood: Boolean = true,
        /**
         * Bot是否在线
         */
        val online: Boolean,
        /**
         * OneBot实现是否状态良好
         */
        val good: Boolean,
    )
}
