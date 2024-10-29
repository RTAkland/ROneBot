/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package test

import cn.rtast.rob.ROneBotFactory

suspend fun main() {
    val bot = ROneBotFactory.createServer(8888, "114514ghpA@", TestClient())
}