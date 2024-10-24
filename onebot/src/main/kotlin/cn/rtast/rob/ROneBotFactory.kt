/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.util.CommandManager
import cn.rtast.rob.util.ob.OneBotListener


object ROneBotFactory {

    /**
     * 存储所有的Bot实例
     * WIP: 暂时还没有作用
     */
    internal val botInstances = mutableListOf<BotInstance>()

    /**
     * 在全局作用域的命令管理器
     */
    val commandManager = CommandManager()

    /**
     * 创建一个Websocket客户端连接到OneBot实现
     * 返回一个创建的Bot实例对象
     */
    fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512
    ): BotInstance {
        val instance =
            BotInstance(address, accessToken, listener, autoReconnect, messageQueueLimit, 0, InstanceType.Client)
                .createBot()
        botInstances.add(instance)
        return instance
    }

    /**
     * 监听一个指定的端口来监听Websocket连接
     * 让OneBot实现作为客户端连接到ROB
     * 返回一个创建的Bot实例对象
     */
    fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512
    ): BotInstance {
        val instance = BotInstance(
            "0.0.0.0",
            accessToken,
            listener,
            autoReconnect,
            messageQueueLimit,
            port,
            InstanceType.Server
        ).createBot()
        botInstances.add(instance)
        return instance
    }
}