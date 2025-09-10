/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/11/25, 3:52 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.command

import cn.rtast.rob.milky.event.ws.raw.ReceiveMessage

public data class CommandRequest(
    val message: ReceiveMessage,
    val args: List<String>
)