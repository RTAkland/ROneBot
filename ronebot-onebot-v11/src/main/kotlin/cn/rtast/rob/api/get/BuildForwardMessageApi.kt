/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.api.get

import cn.rtast.rob.segment.InternalBaseSegment
import java.util.UUID

internal data class BuildForwardMessageApi(
    val params: Params,
    val echo: UUID,
    val action: String = "send_forward_msg",
) {
    data class Params(
        val messages: List<InternalBaseSegment>
    )
}