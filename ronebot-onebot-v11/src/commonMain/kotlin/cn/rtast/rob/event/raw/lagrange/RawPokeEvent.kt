/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.onebot.OneBotAction
import com.google.gson.annotations.SerializedName

public data class RawPokeEvent(
    @ExcludeField
    var action: OneBotAction,
    /**
     * 戳一戳的类型, 例如`戳一戳`, `揉了揉`, `捏了捏`
     */
    @SerializedName("action")
    val pokeAction: String,
    /**
     * 戳一戳后面追加的文字
     */
    val suffix: String,
    /**
     * 戳一戳类型的额外图片URL
     */
    @SerializedName("action_img_url")
    val actionImgUrl: String,
    /**
     * 戳一戳的人的QQ号
     */
    @SerializedName("sender_id")
    val senderId: Long,
    /**
     * 被戳一戳的人的QQ号
     */
    @SerializedName("target_id")
    val targetId: Long,
    /**
     * QQ号
     */
    @SerializedName("user_id")
    val userId: Long,
    /**
     * 群号
     */
    @SerializedName("group_id")
    val groupId: Long?,
)