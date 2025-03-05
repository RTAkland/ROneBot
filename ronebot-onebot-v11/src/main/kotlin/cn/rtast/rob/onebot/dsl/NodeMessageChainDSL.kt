/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/3
 */

@file:Suppress("unused")

package cn.rtast.rob.onebot.dsl

import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.onebot.NodeMessageChain

/**
 * dsl 的方式添加一个合并转发消息链
 */
public inline fun nodeMessageChain(builder: NodeMessageChain.Builder.() -> Unit): NodeMessageChain =
    NodeMessageChain.Builder().apply(builder).build()

/**
 * dsl 的方式添加一个消息链
 */
public fun NodeMessageChain.Builder.messageChain(userId: Long, chain: MessageChain.Builder.() -> Unit) = apply {
    val built = MessageChain.Builder().apply(chain).build()
    this.addMessageChain(built, userId)
}