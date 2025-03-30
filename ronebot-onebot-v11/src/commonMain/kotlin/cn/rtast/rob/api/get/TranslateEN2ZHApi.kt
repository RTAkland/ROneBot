/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 将英文翻译成中文
 * Napcat
 */
@Serializable
internal data class TranslateEN2ZHApi(
    val action: String = "translate_en2zh",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        val words: List<String>
    )
}