/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class SetGroupAvatarApi(
    val params: Params,
    val action: String = "set_group_portrait",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val file: String
    )
}

@Serializable
internal data class SetGroupAvatar(val status: String)