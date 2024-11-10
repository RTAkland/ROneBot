/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

package test

import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.segment.Text
import cn.rtast.rob.segment.toMessageChain


fun main() {
    println(Text("1") + Text("2"))
    val l1 = listOf(Text("1"), Text("2")).apply { this + (Text("3")) }
    println(MessageChain.Builder().build() + Text("1"))
    val l2 = listOf(Text("3"), Text("4"))
    val a = listOf(Text("1"), Text("2"), Text("3"), Text("4")).toMessageChain()
    println(a)
}
