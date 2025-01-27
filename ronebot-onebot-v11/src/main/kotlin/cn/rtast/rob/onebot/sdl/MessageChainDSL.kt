/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/27
 */


package cn.rtast.rob.onebot.sdl

import cn.rtast.rob.onebot.MessageChain

/**
 * dsl 风格的构造一个[MessageChain]
 */
inline fun messageChain(builder: MessageChain.Builder.() -> Unit) =
    MessageChain.Builder().apply(builder).build()

