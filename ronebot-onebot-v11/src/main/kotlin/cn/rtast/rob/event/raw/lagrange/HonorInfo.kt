/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.event.raw.lagrange

import com.google.gson.annotations.SerializedName

public data class HonorInfo(
    val data: HonorInfo
) {
    public data class HonorInfo(
        /**
         * 群号
         */
        @SerializedName("group_id")
        val groupId: Long,
        /**
         * 当前龙王
         */
        @SerializedName("current_talkactive")
        val currentTalkActive: CurrentTalkAction,
        /**
         * 历史龙王
         */
        @SerializedName("talkative_list")
        val talkActiveList: List<TalkActiveList>,
        /**
         * 群聊炽焰
         */
        @SerializedName("legend_list")
        val legendList: List<LegendList>,
        /**
         * 冒尖小春笋
         */
        @SerializedName("strong_newbie_list")
        val strongNewbieList: List<StringNewbieList>,
        /**
         * 快乐之源
         */
        @SerializedName("emotion_list")
        val emotionList: List<EmotionList>,

        /**
         * 群聊之火
         */
        @SerializedName("performer_list")
        val performerList: List<PerformerList>
    )

    public data class CurrentTalkAction(
        /**
         * 内部ID
         */
        val uin: Long,
        /**
         * 持续天数
         */
        @SerializedName("day_count")
        val dayCount: Int,
        /**
         * 头像URL
         */
        val avatar: String,
        /**
         * 头像尺寸
         */
        @SerializedName("avatar_size")
        val avatarSize: Int,
        /**
         * 昵称
         */
        val nick: String
    )

    public data class TalkActiveList(
        val uin: Long,
        val avatar: String,
        val name: String,
        /**
         * 荣誉描述
         */
        val description: String,
        /**
         * 不知道干啥的
         */
        val btnText: String,
        /**
         * 不知道干啥的
         */
        val text: String
    )

    public data class LegendList(
        val uin: Long,
        val avatar: String,
        val name: String,
        val description: String,
        val btnText: String,
        val text: String,
        val icon: String,
    )

    public data class EmotionList(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        val text: String,
        val description: String,
        val icon: Int,
        val btnText: String
    )

    public data class StringNewbieList(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        /**
         * 荣誉描述
         */
        val description: Int
    )

    public data class PerformerList(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        val description: Int
    )
}