import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.util.SatoriListener

/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

class RSatori: SatoriListener {
    override suspend fun onReady(loginInfo: LoginInfo) {
    }

    override suspend fun onPong() {
    }

    override suspend fun onGroupMessage() {
    }

    override suspend fun onPrivateMessage() {
    }

    override suspend fun onChannelMessage() {
        TODO("Not yet implemented")
    }
}

fun main() {
    val client = RSatoriFactory.createClient("ws://127.0.0.1:9999", "114514ghpA@", RSatori())
}