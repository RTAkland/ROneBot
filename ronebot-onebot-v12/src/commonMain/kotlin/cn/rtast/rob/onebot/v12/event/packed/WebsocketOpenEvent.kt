/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 08:12
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.event.packed

import cn.rtast.rob.onebot.v12.event.OneBot12Event
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Action

public data class WebsocketOpenEvent(
    override val action: OneBot12Action,
) : OneBot12Event