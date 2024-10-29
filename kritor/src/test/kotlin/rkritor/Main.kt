/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package rkritor

import cn.rtast.rob.kritor.RKritorFactory
import cn.rtast.rob.kritor.util.MessageChain

suspend fun main() {
    val instance = RKritorFactory.createClient("127.0.0.1", 9000, "3781274982", "114514ghpA@")
    val msg = MessageChain.Builder().addText("114514").build()
    instance.action.sendGroupMessage(985927054, msg)
}