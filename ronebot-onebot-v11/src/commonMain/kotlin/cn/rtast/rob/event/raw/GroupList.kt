/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw

import kotlinx.serialization.Serializable

/**
 * 群列表
 */
@Serializable
public data class GroupList(
    val data: List<GroupInfo.GroupInfo>
)