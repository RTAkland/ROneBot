/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

@file:Suppress("unused")

package cn.rtast.rob.util

import cn.rtast.rob.BaseBotInstance

interface IBotManager<AB : List<B>, B: BaseBotInstance> {

    /**
     * 通过类似下标的方式获取Bot实例
     */
    suspend operator fun get(id: ID): B?

    /**
     * 获取所有的Bot实例
     */
    suspend fun allBots(): AB

    /**
     * 添加Bot实例
     */
    suspend fun addBotInstance(botInstance: B): Boolean

    /**
     * 移除Bot实例
     */
    suspend fun removeBotInstance(botInstance: B): Boolean

    /**
     * ID对象
     */
    data class ID(val id: String)
}

/**
 * 将一个整形转换成ID对象
 */
val Int.ID get() = IBotManager.ID(this.toString())

/**
 * 将一个长整形转换成ID对象
 */
val Long.ID get() = IBotManager.ID(this.toString())

/**
 * 将一个字符串转换成ID对象
 */
val String.ID get() = IBotManager.ID(this)