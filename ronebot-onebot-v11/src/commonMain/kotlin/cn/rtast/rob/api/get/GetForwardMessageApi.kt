/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetForwardMessageApi(
    val params: Params,
    val echo: Uuid,
    val action: String = "get_forward_msg",
) {
    @Serializable
    data class Params(
        val id: String,
    )
}