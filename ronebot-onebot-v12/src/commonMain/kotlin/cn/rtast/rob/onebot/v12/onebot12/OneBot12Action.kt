/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:46
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.onebot12

import cn.rtast.rob.SendAction
import cn.rtast.rob.onebot.v12.BotInstance

public class OneBot12Action internal constructor(
    private val botInstance: BotInstance
) : SendAction{
    override suspend fun send(message: String) {
    }
}