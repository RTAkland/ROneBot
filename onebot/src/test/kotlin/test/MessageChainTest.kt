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
import cn.rtast.rob.segment.Text
import cn.rtast.rob.util.ob.plus
import cn.rtast.rob.util.ob.toMessageChain


fun main() {
    val operator = AT(3458671395) +
            Text("114514") +
            Image("https://static.rtast.cn/images/%E5%8F%88%E6%8B%8D%E4%BA%91_logo2.png") +
            Face(666) +
            NewLine()
    val a = listOf(Text("1"), Text("2"), Text("3"), Text("4")).toMessageChain()
    println(a)
    println(operator)
}
