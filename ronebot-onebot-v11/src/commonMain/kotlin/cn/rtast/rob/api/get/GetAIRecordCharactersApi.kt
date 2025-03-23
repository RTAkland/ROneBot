/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetAIRecordCharactersApi(
    val action: String = "get_ai_characters",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("chat_type")
        val chatType: UInt,
    )
}

@Serializable
public data class AIRecordCharacters(
    val data: List<AICharacters>
) {
    @Serializable
    public data class AICharacters(
        val type: String,
        val characters: List<Character>
    )

    @Serializable
    public data class Character(
        @SerialName("character_id")
        val characterId: String,
        @SerialName("character_name")
        val characterName: String,
        @SerialName("preview_url")
        val previewUrl: String,
    )
}