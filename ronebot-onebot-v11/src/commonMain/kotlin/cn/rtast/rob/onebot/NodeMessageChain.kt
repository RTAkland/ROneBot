/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */

@file:Suppress("unused")

package cn.rtast.rob.onebot

import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.segment.INode
import cn.rtast.rob.segment.InternalBaseSegment
import kotlin.jvm.JvmOverloads

/**
 * 使用数组消息链([MessageChain])来构造一个合并转发消息链([NodeMessageChain])
 */
public class NodeMessageChain internal constructor(internal val nodes: List<InternalBaseSegment>) : IMessageChain {

    override fun toString(): String {
        return "NodeMessageChain{${nodes.joinToString()}}"
    }

    override val isEmpty: Boolean get() = nodes.isEmpty()

    override val size: Int get() = nodes.size

    public class Builder {
        private val _nodes = mutableListOf<INode>()

        /**
         * 添加一个数组消息链([MessageChain])到一个Node([NodeMessageChain])
         */
        public fun addMessageChain(messageChain: MessageChain, userId: Long, nickname: String = ""): Builder {
            val node = INode(INode.Data(nickname, userId.toString(), messageChain.finalArrayMsgList))
            _nodes.add(node)
            return this
        }

        public fun build(): NodeMessageChain {
            return NodeMessageChain(_nodes)
        }

        override fun toString(): String {
            return "NodeMessageChain{${_nodes.joinToString()}}"
        }
    }
}