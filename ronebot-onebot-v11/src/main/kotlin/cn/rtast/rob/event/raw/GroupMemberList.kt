/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw

import cn.rtast.rob.enums.UserSex
import com.google.gson.annotations.SerializedName

/**
 * 群成员列表
 */
public data class GroupMemberList(
    val data: List<MemberInfo>
) {
    public data class MemberInfo(
        /**
         * 年龄
         */
        val age: Int,
        /**
         * 地区
         */
        val area: String,
        /**
         * 群昵称
         */
        val card: String?,
        /**
         * 群昵称是否可以变更
         */
        @SerializedName("card_changeable")
        val cardChangeable: Boolean,
        /**
         * 群号
         */
        @SerializedName("group_id")
        val groupId: Long,
        /**
         * 加群时间
         */
        @SerializedName("join_time")
        val joinTime: Long,
        /**
         * 上一次发言时间
         */
        @SerializedName("last_sent_time")
        val lastSentTime: Long,
        /**
         * 等级
         */
        val level: String,
        /**
         * 昵称
         */
        val nickname: String,
        /**
         * 权限组
         */
        val role: String,
        /**
         * 性别
         */
        val sex: UserSex,
        /**
         * 头衔
         */
        val title: Any,
        /**
         * 头衔过期事件
         */
        @SerializedName("title_expire_time")
        val titleExpireTime: Long,
        /**
         * 是否为莫陌生人
         */
        val unfriendly: Boolean,
        /**
         * QQ号
         */
        @SerializedName("user_id")
        val userId: Long
    )
}