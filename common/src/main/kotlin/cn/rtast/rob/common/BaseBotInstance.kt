/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.common

interface BaseBotInstance {
    /**
     * 创建Bot
     */
    fun createBot(): BaseBotInstance

    /**
     * 释放一个Bot实例
     */
    fun disposeBot()
}