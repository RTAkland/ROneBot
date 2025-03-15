/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/15
 */

package cn.rtast.rob.example

import cn.rtast.rob.OneBotFactory

suspend fun main() {
    OneBotFactory.createServer(9999, "114514")
}