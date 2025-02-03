/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

package test

import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.onebot.dsl.text
import cn.rtast.rob.segment.Text


fun main() {
    val msg = messageChain {
        addText("Hello World")
        this(Text("1111"))
        invoke(Text("2222"))
        +Text("22222")
        add(Text("22222"))
        text {

        }
    }
}
