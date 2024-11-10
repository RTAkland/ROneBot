/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

package test

import cn.rtast.rob.segment.Text
import cn.rtast.rob.onebot.toMessageChain
import cn.rtast.rob.segment.plus


fun main() {
    println(Text("1") + Text("2"))
    val a = listOf(Text("1"), Text("2"), Text("3"), Text("4")).toMessageChain()
    println(a)
}
