/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */

@file:Suppress("unused")

package cn.rtast.rob.onebot

import cn.rtast.rob.segment.INode

/**
 * 使用数组消息链([MessageChain])来构造一个合并转发消息链([NodeMessageChain])
 */
class NodeMessageChain internal constructor(internal val nodes: List<INode>) {

    override fun toString(): String {
        return "NodeMessageChain{${nodes.joinToString()}}"
    }

    class Builder {
        private val _nodes = mutableListOf<INode>()

        /**
         * 添加一个数组消息链([MessageChain])到一个Node([NodeMessageChain])
         */
        @JvmOverloads
        fun addMessageChain(messageChain: MessageChain, userId: Long, nickname: String = ""): Builder {
            val node = INode(INode.Data(nickname, userId.toString(), messageChain.finalArrayMsgList))
            _nodes.add(node)
            return this
        }

        fun build(): NodeMessageChain {
            return NodeMessageChain(_nodes)
        }

        override fun toString(): String {
            return "NodeMessageChain{${_nodes.joinToString()}}"
        }
    }
}