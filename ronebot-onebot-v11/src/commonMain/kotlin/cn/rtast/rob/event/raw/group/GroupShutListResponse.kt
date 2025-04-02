/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.event.raw.group

import kotlinx.serialization.Serializable

@Serializable
public data class GroupShutListResponse(
    val data: List<GroupShutListUser>
) {
    @Serializable
    public data class GroupShutListUser(
        val uid: String,
        val qid: String,
        val uin: String,
        val nick: String,
        val remark: String,
        val cardType: Int,
        val cardName: String,
        val role: Int,
        val avatarPath: String,
        val shutUpTime: Long,
        val isDelete: Boolean,
        val isSpecialConcerned: Boolean,
        val isSpecialShield: Boolean,
        val isRobot: Boolean,
        val groupHonor: Map<String, Int>,
        val memberRealLevel: Int,
        val memberLevel: Int,
        val globalGroupLevel: Int,
        val globalGroupPoint: Int,
        val memberTitleId: Int,
        val memberSpecialTitle: String,
        val specialTitleExpireTime: String,
        val userShowFlag: Int,
        val userShowFlagNew: Int,
        val richFlag: Int,
        val mssVipType: Int,
        val bigClubLevel: Int,
        val bigClubFlag: Int,
        val autoRemark: String,
        val creditLevel: Int,
        val joinTime: Long,
        val lastSpeakTime: Long,
        val memberFlag: Int,
        val memberFlagExt: Int,
        val memberMobileFlag: Int,
        val memberFlagExt2: Int,
        val isSpecialShielded: Boolean,
        val cardNameId: Int
    )
}