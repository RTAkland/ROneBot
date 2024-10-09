/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.GroupMessage
import cn.rtast.rob.satori.entity.GroupRevokeMessage
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.entity.PrivateMessage
import cn.rtast.rob.satori.entity.PrivateRevokeMessage
import cn.rtast.rob.satori.util.SatoriListener

class RSatori : SatoriListener {
    override suspend fun onReady(loginInfo: LoginInfo) {
        println(loginInfo)
    }

    override suspend fun onPong() {
        println("pong")
    }

    override suspend fun onGroupMessage(message: GroupMessage) {
        println(message)
    }

    override suspend fun onPrivateMessage(message: PrivateMessage) {
        println(message)
    }

    override suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {
        println(message)
    }

    override suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage) {
        println(message)
    }
}

fun main() {
    val client = RSatoriFactory.createClient("ws://127.0.0.1:9999", "114514ghpA@", RSatori())
}