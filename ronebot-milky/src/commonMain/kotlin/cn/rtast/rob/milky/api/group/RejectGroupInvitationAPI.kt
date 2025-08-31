/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:30 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RejectGroupInvitationAPI(
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 邀请序列号
     */
    @SerialName("invitation_seq")
    val invitationSeq: Long,
    /**
     * 原因
     */
    val reason: String,
)