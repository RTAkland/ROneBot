/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 09:17
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("MessageChainUtil")

package cn.rtast.rob.onebot

/**
 * 将没有构造好的消息链
 * 转换为[NodeMessageChain]
 */
public fun mergeMessageChainBuilderToNode(
    builders: Collection<MessageChain.Builder>,
    senderId: Long
): NodeMessageChain.Builder =
    builders.toNode(senderId)

/**
 * 将消息链转换为[NodeMessageChain]
 */
public fun mergeMessageChainToNode(chain: Collection<MessageChain>, senderId: Long): NodeMessageChain =
    chain.toNode(senderId)