/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/31
 */

@file:Suppress("unused", "DEPRECATION")

package cn.rtast.rob.onebot.dsl

import cn.rtast.rob.onebot.CQMessageChain

/**
 * dsl 风格构造一个[CQMessageChain]
 */
public inline fun cqMessageChain(builder: CQMessageChain.Builder.() -> Unit): CQMessageChain =
    CQMessageChain.Builder().apply(builder).build()
