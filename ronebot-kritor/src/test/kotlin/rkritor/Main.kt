/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package rkritor

import cn.rtast.rob.kritor.RKritorFactory
import cn.rtast.rob.kritor.util.msg.MessageChain

suspend fun main() {
    val instance = RKritorFactory.createClient("127.0.0.1", 9000, "3781274982", "114514ghpA@")

}