/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw

import kotlinx.serialization.Serializable

/**
 * 群成员信息
 */
@Serializable
public data class GroupMemberInfo(
    val data: GroupMemberList.MemberInfo,
)