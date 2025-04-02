/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.event.raw.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class HonorInfo(
    val data: HonorInfo
) {
    @Serializable
    public data class HonorInfo(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 当前龙王
         */
        @SerialName("current_talkactive")
        val currentTalkActive: CurrentTalkAction,
        /**
         * 历史龙王
         */
        @SerialName("talkative_list")
        val talkActiveList: List<TalkActiveList>,
        /**
         * 群聊炽焰
         */
        @SerialName("legend_list")
        val legendList: List<LegendList>,
        /**
         * 冒尖小春笋
         */
        @SerialName("strong_newbie_list")
        val strongNewbieList: List<StringNewbieList>,
        /**
         * 快乐之源
         */
        @SerialName("emotion_list")
        val emotionList: List<EmotionList>,

        /**
         * 群聊之火
         */
        @SerialName("performer_list")
        val performerList: List<PerformerList>
    )

    @Serializable
    public data class CurrentTalkAction(
        /**
         * 内部ID
         */
        val uin: Long,
        /**
         * 持续天数
         */
        @SerialName("day_count")
        val dayCount: Int,
        /**
         * 头像URL
         */
        val avatar: String,
        /**
         * 头像尺寸
         */
        @SerialName("avatar_size")
        val avatarSize: Int,
        /**
         * 昵称
         */
        val nick: String
    )

    @Serializable
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

    @Serializable
    public data class LegendList(
        val uin: Long,
        val avatar: String,
        val name: String,
        val description: String,
        val btnText: String,
        val text: String,
        val icon: String,
    )

    @Serializable
    public data class EmotionList(
        @SerialName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        val text: String,
        val description: String,
        val icon: Int,
        val btnText: String
    )

    @Serializable
    public data class StringNewbieList(
        @SerialName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        /**
         * 荣誉描述
         */
        val description: Int
    )

    @Serializable
    public data class PerformerList(
        @SerialName("user_id")
        val userId: Long,
        val nickname: String,
        val avatar: String,
        val description: Int
    )
}