/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

package test

import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.Text
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.plus


fun main() {
    MessageChain.Builder().addText("1").build() + Text("")
    println((AT(3458671395) + Text("") + Text("1111") + Text("1111")))
}
