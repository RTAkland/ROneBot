/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetStrangerInfoApi(
    val action: String = "get_stranger_info",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        @SerialName("no_cache")
        val noCache: Boolean,
    )
}