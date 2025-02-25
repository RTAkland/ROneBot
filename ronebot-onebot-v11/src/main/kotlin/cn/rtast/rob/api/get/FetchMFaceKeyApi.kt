/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class FetchMFaceKeyApi(
    val params: Params,
    val action: String = "fetch_mface_key",
    val echo: UUID
) {
    data class Params(
        @SerializedName("emoji_ids")
        val emojiIds: List<String>,
    )
}

internal data class FetchMFaceKey(
    val data: List<String>
)