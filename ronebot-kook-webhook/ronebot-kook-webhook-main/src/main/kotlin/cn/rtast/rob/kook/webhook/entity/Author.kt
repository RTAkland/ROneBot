/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.entity

import com.google.gson.annotations.SerializedName

public data class Author(
    val id: String,
    val username: String,
    @SerializedName("identify_num")
    val identifyNum: String,
    val online: Boolean,
    val os: String,
    val status: Int,
    val avatar: String,
    @SerializedName("vip_avatar")
    val vipAvatar: String,
    val banner: String,
    val nickname: String,
    val roles: List<Long>,
    @SerializedName("is_vip")
    val isVip: Boolean,
    @SerializedName("vip_amp")
    val vipAmp: Boolean,
    val bot: Boolean,
    val nameplate: List<String>,
    @SerializedName("decorations_id_map")
    val decorationsIdMap: String?,
    @SerializedName("is_sys")
    val isSys: Boolean,
    @SerializedName("mobile_verified")
    val mobileVerified: Boolean,
)