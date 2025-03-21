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
    public expect suspend fun createBot(): BaseBotInstance

    /**
     * 释放一个Bot实例
     */
    public expect suspend fun disposeBot()

    /**
     * 用于判断action是否已经初始化完成
     */
    public expect val isActionInitialized: Boolean
}