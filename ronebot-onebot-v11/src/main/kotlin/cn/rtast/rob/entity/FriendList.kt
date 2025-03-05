/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

public data class FriendList(
    val data: List<Friend>
) {
    public data class Friend(
        /**
         * QQ号
         */
        @SerializedName("user_id")
        val userId: Long,
        /**
         * qid
         */
        @SerializedName("q_id")
        val qId: String,
        /**
         * 昵称
         */
        val nickname: String,
        /**
         * 备注
         */
        val remark: String,
        /**
         * 两个人都在的群内
         */
        val group: Group
    )

    public data class Group(
        /**
         * 群ID
         */
        @SerializedName("group_id")
        val groupId: Long,
        /**
         * 群名字
         */
        @SerializedName("group_name")
        val groupName: String,
    )
}