/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package rkritor

import cn.rtast.rob.kritor.RKritorFactory
import cn.rtast.rob.kritor.kritor.KritorAction
import cn.rtast.rob.kritor.kritor.KritorListener
import cn.rtast.rob.kritor.kritor.MessageChain
import cn.rtast.rob.util.encodeToBase64
import io.kritor.common.PushMessageBody
import java.net.URI

class Bot : KritorListener {
    override suspend fun onMessage(message: PushMessageBody, action: KritorAction) {
        println(message.group.uin)
        println(message)

        val url = URI("https://static.rtast.cn/images/%E5%8F%88%E6%8B%8D%E4%BA%91_logo7.png")
            .toURL().readBytes().encodeToBase64()
        val msg = MessageChain.Builder().addImage(url, true).build()
        action.sendGroupMessage(message.group.groupId.toLong(), msg)
    }
}

suspend fun main() {
    val instance = RKritorFactory.createClient("127.0.0.1", 9000, "3781274982", "114514ghpA@", Bot())
}