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

@Serializable
public data class GroupMember(
    /**
     * qq号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 昵称
     */
    @SerialName("nickname")
    val nickname: String,
    /**
     * 性别
     */
    val sex: UserSex,
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 	成员备注
     */
    val card: String,
    /**
     * 专属头衔
     */
    val title: String,
    /**
     * 群等级，注意和 QQ 等级区分
     */
    val level: Int,
    /**
     * 权限等级
     */
    val role: GroupMemberRole,
    /**
     * 入群时间，Unix 时间戳（秒）
     */
    @SerialName("join_time")
    val joinTime: Long,
    /**
     * 最后发言时间，Unix 时间戳（秒）
     */
    @SerialName("last_sent_time")
    val lastSentTime: Long,
    /**
     * 禁言结束时间，Unix 时间戳（秒）
     */
    @SerialName("shut_up_end_time")
    val shutUpEndTime: Long?
)