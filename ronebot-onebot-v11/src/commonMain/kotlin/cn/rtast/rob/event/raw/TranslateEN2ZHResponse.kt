/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.event.raw

import kotlinx.serialization.Serializable

/**
 * 翻译结果
 * Napcat
 * @see cn.rtast.rob.api.get.TranslateEN2ZHApi
 */
@Serializable
public data class TranslateEN2ZHResponse(
    val data: List<String>
)