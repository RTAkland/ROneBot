/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/31
 */

package cn.rtast.rob.event.raw.group

import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawGroupNameChangeEvent(
    /**
     * 群号
     */
    val groupId: Long,
    /**
     * BotQQ号
     */
    @SerialName("self_id")
    val selfId: Long,
    /**
     * 新的群名字
     */
    val name: String,
) {
    @Transient
    lateinit var action: OneBotAction
}