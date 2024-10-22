/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.out.lagrange.get

import cn.rtast.rob.enums.internal.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class FetchMFaceKeyOut(
    val params: Params,
    val action: String = "fetch_mface_key",
    val echo: MessageEchoType = MessageEchoType.FetchMFaceKey
) {
    data class Params(
        @SerializedName("emoji_ids")
        val emojiIds: List<String>,
    )
}

internal data class FetchMFaceKey(
    val data: List<String>
)