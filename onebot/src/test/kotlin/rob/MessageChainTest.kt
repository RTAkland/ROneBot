/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

package rob

import cn.rtast.rob.util.ob.asMessageChain
import cn.rtast.rob.util.ob.asMessageChainBuilder


fun main() {
    println(null.asMessageChain())
    println("114514".asMessageChain())
    println("1919810".asMessageChainBuilder())
    val msgList = listOf("", null, 1)
    println(msgList.asMessageChain(false, true))
}
