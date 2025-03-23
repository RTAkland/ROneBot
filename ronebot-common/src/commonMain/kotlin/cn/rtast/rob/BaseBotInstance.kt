/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob

public interface BaseBotInstance {
    /**
     * 创建Bot
     */
    public suspend fun createBot(): BaseBotInstance

    /**
     * 释放一个Bot实例
     */
    public suspend fun disposeBot()

    /**
     * 用于判断action是否已经初始化完成
     */
    public val isActionInitialized: Boolean
}