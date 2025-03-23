/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class FetchMFaceKeyApi(
    val params: Params,
    val action: String = "fetch_mface_key",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("emoji_ids")
        val emojiIds: List<String>,
    )
}

@Serializable
internal data class FetchMFaceKey(
    val data: List<String>
)