/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */

@file:Suppress("unused")

package cn.rtast.rob.onebot

import cn.rtast.rob.segment.toMessageChainBuilderInternal
import cn.rtast.rob.segment.toMessageChainInternal


/**
 * 将一个集合([Collection])的[MessageChain.Builder]对象转换成合并转发消息链([NodeMessageChain.Builder])
 * 并且返回未构造的[NodeMessageChain.Builder]对象
 */
fun Collection<MessageChain.Builder>.toNode(senderId: Long): NodeMessageChain.Builder {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it.build(), senderId) }
    return node
}

/**
 * 将一个集合([Collection])的[MessageChain]对象转换成合并转发消息链([NodeMessageChain])
 * 并且返回未构造的[NodeMessageChain]对象
 */
fun Collection<MessageChain>.toNode(senderId: Long): NodeMessageChain {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it, senderId) }
    return node.build()
}

/**
 * 将一个任意类型的集合[Collection]转换为一个构造好的消息链
 * 并且接收一个参数表示是否在每个元素之后插入一个换行符
 * ***注意: 元素必须重写了[toString]方法, 如果一个元素没有重写[toString]方法则会使用这个元素的内存地址***
 */
@JvmOverloads
fun <T> Collection<T?>.toMessageChainString(newLine: Boolean = false, filterNull: Boolean = false): MessageChain {
    val msg = MessageChain.Builder()
    if (filterNull) this.filter { it != null }.forEach {
        msg.addText(it.toString())
        if (newLine) msg.addNewLine()
    } else {
        this.forEach {
            msg.addText(it.toString())
            if (newLine) msg.addNewLine()
        }
    }
    return msg.build()
}

/**
 * 将任意类型的集合[Collection]转换为未构造的消息链构造器
 */
@JvmOverloads
fun <T> Collection<T>.toMessageChainBuilderString(newLine: Boolean = false): MessageChain.Builder {
    val msgBuilder = MessageChain.Builder()
    this.forEach {
        msgBuilder.addText(it.toString())
        if (newLine) msgBuilder.addNewLine()
    }
    return msgBuilder
}

/**
 * 将任意类型的数据转换成[MessageChain], 但是最终都会调用这个类型的[toString]方法
 */
fun <T> T?.toMessageChain() = MessageChain.Builder().addText(this.toString()).build()

/**
 * 使两个[MessageChain]对象可以快速拼接起来合并成一个
 * 完整的[MessageChain]
 */
@Deprecated("Use chain builder instead", level = DeprecationLevel.HIDDEN)
operator fun MessageChain.plus(other: MessageChain): MessageChain {
    this.finalArrayMsgList.addAll(other.finalArrayMsgList)
    return this.finalArrayMsgList.toMessageChainInternal()
}

/**
 * 使两个[MessageChain.Builder]对象可以快速拼接起来合并成一个
 * 完整的[MessageChain.Builder]
 */
@Deprecated("Use chain builder instead", level = DeprecationLevel.HIDDEN)
operator fun MessageChain.Builder.plus(other: MessageChain.Builder): MessageChain.Builder {
    this.addRawArrayMessage(other.arrayMessageList)
    return this.arrayMessageList.toMessageChainBuilderInternal()
}
