/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

package test

import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.Face
import cn.rtast.rob.segment.Image
import cn.rtast.rob.segment.NewLine
import cn.rtast.rob.segment.Reply
import cn.rtast.rob.segment.Text
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.plus


fun main() {
    // 可以直接对一个链式调用构造的消息使用操作符重载
    val chain = MessageChain.Builder().addText("1").build() + Text("")
    println(chain)
    val operator = AT(3458671395) +
            Text("114514") +
            Image("https://example.com/example.png") +
            Reply(114514L) +
            Face(666) +
            NewLine() +
            ""
    println(operator)
}
