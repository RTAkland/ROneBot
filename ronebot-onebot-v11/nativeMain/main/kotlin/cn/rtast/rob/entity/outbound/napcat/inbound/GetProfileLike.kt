/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.entity.outbound.napcat.inbound

import com.google.gson.annotations.SerializedName

data class GetProfileLike(
    val data: ProfileLike
) {
    data class ProfileLike(
        @SerializedName("total_count")
        val totalCount: Long,
        @SerializedName("new_count")
        val newCount: Long,
        @SerializedName("new_nearby_count")
        val newNearbyCount: Long,
        @SerializedName("last_visit_time")
        val lastVisitTime: Long,
        val userInfos: List<UserInfo>
    )

    data class UserInfo(
        val uid: String,
        val src: Int,
        val latestTime: Long,
        val count: Long,
        val customId: Long,
        val lastCharged: Long,
        val bAvailableCnt: Long,
        val bTodayVotedCnt: Long,
        val nick: String,
        val gender: Int,
        val age: Int,
        val isFriend: Boolean,
        @SerializedName("isvip")
        val isVip: Boolean,
        @SerializedName("isSvip")
        val isSVip: Boolean,
        val uin: Long,
    )
}