/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:22 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Friend(
    /**
     * qq号
     */
    @SerialName("user_id")
    val userId: Long,

    /**
     * QID
     */
    val qid: String,

    /**
     * 昵称
     */
    val nickname: String,

    /**
     * 备注
     */
    val remark: String,

    /**
     * 分组
     */
    val category: Category,
)