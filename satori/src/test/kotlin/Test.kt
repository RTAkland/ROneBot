/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.GuildMessage
import cn.rtast.rob.satori.enums.GuildUserRole
import cn.rtast.rob.satori.enums.Platforms
import cn.rtast.rob.satori.satori.SatoriAction
import cn.rtast.rob.satori.satori.SatoriListener
import org.java_websocket.handshake.ServerHandshake

class RSatori : SatoriListener {
    override suspend fun onGroupMessage(message: GuildMessage.Message) {
        if (message.guild!!.id == "985927054") {
            println(message.action.setGuildMemberRole(message.guild.id, message.member!!.user.id, GuildUserRole.admin))
        }
    }

    override suspend fun onWebsocketError(action: SatoriAction, e: Exception) {
        e.printStackTrace()
    }

    override suspend fun onWebsocketOpen(action: SatoriAction, handshake: ServerHandshake) {
        println("open")
    }
}

suspend fun main() {
    val client =
        RSatoriFactory.createClient("ws://127.0.0.1:9999", "3458671395", "114514ghpA@", RSatori(), Platforms.QQ)
}