/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.util.SatoriListener

class RSatori : SatoriListener {
}

fun main() {
    val client = RSatoriFactory.createClient("ws://127.0.0.1:9999", "114514ghpA@", true, RSatori())
}