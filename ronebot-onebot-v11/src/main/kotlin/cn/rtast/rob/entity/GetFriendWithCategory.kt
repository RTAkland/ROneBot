/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class GetFriendWithCategory(
    val data: List<FriendCategory>,
) {
    data class FriendCategory(
        val buddyList: List<Buddy>,
        val categoryId: Int,
        val categoryMbCount: Int,
        val categoryName: String,
        val categorySortId: Int,
        val onlineCount: Int
    )

    data class Buddy(
        val age: Int,
        @SerializedName("birthday_day")
        val birthdayDay: Int,
        @SerializedName("birthday_month")
        val birthdayMonth: Int,
        @SerializedName("birthday_year")
        val birthdayYear: Int,
        val categoryId: Int,
        val eMail: String,
        val level: Int,
        val longNick: String,
        val nickname: String,
        val qid: String,
        val remark: String,
        val richTime: Int,
        val sex: String,
        val uid: String,
        val uin: String,
        @SerializedName("user_id")
        val userId: Long
    )
}