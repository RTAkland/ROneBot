/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FriendList(
    val data: List<Friend>
) {
    @Serializable
    public data class Friend(
        /**
         * QQ号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * qid
         */
        @SerialName("q_id")
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

    @Serializable
    public data class Group(
        /**
         * 群ID
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 群名字
         */
        @SerialName("group_name")
        val groupName: String,
    )
}