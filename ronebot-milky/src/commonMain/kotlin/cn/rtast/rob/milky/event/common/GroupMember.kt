/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:30 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import cn.rtast.rob.milky.enums.GroupMemberRole
import cn.rtast.rob.milky.enums.UserSex
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 群成员信息
 */
@Serializable
public data class GroupMember(
    /**
     * 成员备注
     */
    @SerialName("card")
    val card: String,
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 加群时间
     * UNIX时间戳 秒
     */
    @SerialName("join_time")
    val joinTime: Long,
    /**
     * 最后发言时间
     * UNIX时间戳 秒
     */
    @SerialName("last_sent_time")
    val lastSentTime: Long,
    /**
     * 群聊等级
     */
    val level: Int,
    /**
     * 群昵称
     */
    val nickname: String,
    /**
     * 群成员角色
     */
    val role: GroupMemberRole,
    /**
     * 性别
     */
    val sex: UserSex,
    /**
     * 专属头衔
     */
    val title: String?,
    /**
     * qq号
     */
    @SerialName("user_id")
    val userId: Long
)

