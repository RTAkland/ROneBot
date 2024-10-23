/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.util.ob.OneBotListener


class ROneBotFactory {

    internal val botInstances = mutableListOf<BotInstance>()

    /**
     * 创建一个Websocket客户端连接到OneBot实现
     * 返回ROneBotFactory本身
     */
    fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512
    ): BotInstance {
        return BotInstance(address, accessToken, listener, autoReconnect, messageQueueLimit, 0, InstanceType.Client)
            .createBot()
    }

    /**
     * 监听一个指定的端口来监听Websocket连接
     * 让OneBot实现作为客户端连接到ROB
     */
    fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512
    ): BotInstance {
        return BotInstance(
            "0.0.0.0",
            accessToken,
            listener,
            autoReconnect,
            messageQueueLimit,
            port,
            InstanceType.Server
        ).createBot()
    }
}