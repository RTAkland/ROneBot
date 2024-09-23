/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.segment.Node

class NodeMessageChain internal constructor(nodes: List<Node>) {

    val finalNodes = nodes

    class Builder {
        private val _nodes = mutableListOf<Node>()

        fun addNode(node: Node): Builder {
            _nodes.add(node)
            return this
        }

        fun addMessageChain(messageChain: MessageChain, userId: Long, nickname: String = ""): Builder {
            val node = Node(Node.Data(nickname, userId.toString(), messageChain.finalArrayMsgList))
            _nodes.add(node)
            return this
        }

        fun build(): NodeMessageChain {
            return NodeMessageChain(_nodes)
        }
    }
}