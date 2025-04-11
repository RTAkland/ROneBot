/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:09
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.entity

import cn.rtast.rob.annotations.JsPlatformOnly
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@JsPlatformOnly
@Serializable
public data class Packet(
    val params: JsonElement
)