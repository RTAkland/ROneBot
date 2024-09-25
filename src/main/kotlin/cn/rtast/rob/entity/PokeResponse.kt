/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class PokeResponse(
    val action: String,
    val suffix: String,
    @SerializedName("action_img_url")
    val actionImgUrl: String,
    @SerializedName("sender_id")
    val senderId: Long,
    @SerializedName("target_id")
    val targetId: Long,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("group_id")
    val groupId: Long?,
)