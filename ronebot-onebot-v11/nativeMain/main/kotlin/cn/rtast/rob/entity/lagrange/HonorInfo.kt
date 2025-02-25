/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.entity.lagrange

import com.google.gson.annotations.SerializedName

data class HonorInfo(
    val data: HonorInfo
) {
    data class HonorInfo(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("current_talkactive")
        val currentTalkActive: CurrentTalkAction,
        @SerializedName("talkative_list")
        val talkActiveList: List<TalkActiveList>,
        @SerializedName("legend_list")
        val legendList: List<LegendList>,
        @SerializedName("strong_newbie_list")
        val strongNewbieList: List<Any>,
        @SerializedName("emotion_list")
        val emotionList: List<EmotionList>,
    )

    data class CurrentTalkAction(
        val uin: Long,
        @SerializedName("day_count")
        val dayCount: Int,
        val avatar: String,
        @SerializedName("avatar_size")
        val avatarSize: Int,
        val nick: String
    )

    data class TalkActiveList(
        val uin: Long,
        val avatar: String,
        val name: String,
        val description: String,
        val btnText: String,
        val text: String
    )

    data class LegendList(
        val uin: Long,
        val avatar: String,
        val name: String,
        val description: String,
        val btnText: String,
        val text: String,
        val icon: String,
    )

    data class EmotionList(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        val text: String,
        val description: String,
        val icon: Int,
        val btnText: String
    )
}