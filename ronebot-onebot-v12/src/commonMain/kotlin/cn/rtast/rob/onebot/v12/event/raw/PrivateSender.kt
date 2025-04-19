/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 07:53
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.event.raw

import cn.rtast.rob.entity.IPrivateSender
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Action
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class PrivateSender(
    @SerialName("user_id")
    override val userId: Long,
    val nickname: String
) : IPrivateSender {

    @Transient
    public lateinit var action: OneBot12Action
}
