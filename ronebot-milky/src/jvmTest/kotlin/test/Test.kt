/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.util.arrow.success
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class Test {
    @Test
    fun `test api`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot("http://127.0.0.1:8080", "114514", logLevel = LogLevel.DEBUG)
            println(bot.action.getLoginInfo())
            println(bot.action.getFriendList())
            println(bot.action.getFriendInfo(2))
            println(bot.action.getGroupList())
            println(bot.action.getGroupInfo(5787))
            println(bot.action.getGroupMemberInfo(575300987, 3458671395))
            println(bot.action.getGroupMemberList(575300987).success())
        }
    }
}