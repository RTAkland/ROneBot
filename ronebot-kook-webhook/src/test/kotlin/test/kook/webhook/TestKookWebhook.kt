/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package test.kook.webhook

import cn.rtast.rob.kook.webhook.KookBotFactory

suspend fun main() {
    val token = System.getenv("TOKEN")
    val verifyToken = System.getenv("VERIFY_TOKEN")
    val encryptKey = System.getenv("ENCRYPT_KEY")
    val bot = KookBotFactory.createKookBot(9091, token, verifyToken, encryptKey)
}