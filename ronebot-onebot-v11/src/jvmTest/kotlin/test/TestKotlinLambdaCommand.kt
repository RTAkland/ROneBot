/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 9:34 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package test

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.session.accept
import org.junit.Test

class TestKotlinLambdaCommand {

    @Test
    fun `test kotlin lambda command`() {
        val command = object : BaseCommand() {
            override val commandNames: List<String> = listOf("test")

            override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
                startGroupSession(message) {
                    it.accept()
                }
            }
        }
    }
}