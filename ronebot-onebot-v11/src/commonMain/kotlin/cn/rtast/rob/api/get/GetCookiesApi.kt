/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetCookiesApi(
    val action: String = "get_cookies",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        val domain: String,
    )
}