import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.util.SatoriListener

/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

class RSatori: SatoriListener {
    override suspend fun onReady() {

    }
}

fun main() {
    val client = RSatoriFactory.createClient("ws://127.0.0.1:5600", "114514ghpA@", RSatori())
}