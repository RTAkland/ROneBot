/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */

@file:Suppress("unused")

package cn.rtast.rob.onebot


/**
 * 将一个集合([Collection])的[MessageChain.Builder]对象转换成合并转发消息链([NodeMessageChain.Builder])
 * 并且返回未构造的[NodeMessageChain.Builder]对象
 */
public fun Collection<MessageChain.Builder>.toNode(senderId: Long): NodeMessageChain.Builder {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it.build(), senderId) }
    return node
}

/**
 * 将一个集合([Collection])的[MessageChain]对象转换成合并转发消息链([NodeMessageChain])
 * 并且返回未构造的[NodeMessageChain]对象
 */
public fun Collection<MessageChain>.toNode(senderId: Long): NodeMessageChain {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it, senderId) }
    return node.build()
}