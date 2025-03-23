/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetMiniAppArkApi(
    val params: Params,
    val echo: Uuid,
    val action: String = "get_mini_app_ark"
) {
    @Serializable
    data class Params(
        val type: String,
        val title: String,
        val desc: String,
        val picUrl: String,
        val jumpUrl: String,
        val iconUrl: String? = null,
        val sdkId: String? = null,
        val appId: String? = null,
    )
}