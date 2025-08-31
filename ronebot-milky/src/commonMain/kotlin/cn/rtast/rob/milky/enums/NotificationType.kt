/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:26 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class NotificationType {
    /**
     * 普通请求
     */
    @SerialName("join_request")
    JoinRequest,

    /**
     * 邀请他人进群的请求
     */
    @SerialName("invited_join_request")
    InvitedHJoinRequest,

    /**
     * 群成员退群通知
     */
    @SerialName("quit")
    Quit,

    /**
     * 群成员被移除通知
     */
    @SerialName("kick")
    Kick,

    /**
     * 群管理员变更通知
     */
    @SerialName("admin_change")
    AdminChange
}