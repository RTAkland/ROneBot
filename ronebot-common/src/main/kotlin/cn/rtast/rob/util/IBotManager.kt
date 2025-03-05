/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

@file:Suppress("unused")

package cn.rtast.rob.util

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.SendAction
import org.slf4j.Logger

public interface IBotManager<AB : List<B>, B : BaseBotInstance, A : SendAction> {

    public val logger: Logger

    /**
     * 通过类似下标的方式获取Bot实例
     */
    public suspend operator fun get(id: ID): B?

    /**
     * 获取所有的Bot实例
     */
    public fun allBots(): AB

    /**
     * 添加Bot实例
     */
    public suspend fun addBotInstance(botInstance: B)

    /**
     * 移除Bot实例
     */
    public suspend fun removeBotInstance(botInstance: B)

    /**
     * 暂时禁用Bot实例
     */
    public suspend fun disableBotInstance(botInstance: B)

    /**
     * 启用Bot实例
     */
    public suspend fun enableBotInstance(botInstance: B)

    /**
     * 禁用所有Bot实例
     */
    public suspend fun disableAllBots()

    /**
     * 启用所有Bot实例
     */
    public suspend fun enableAllBots()

    /**
     * 获取Bot开启状态
     */
    public suspend fun getBotInstanceStatus(botInstance: B): Boolean

    /**
     * 通过action对象获取Bot实例
     */
    public suspend fun getBotInstanceByAction(action: A): B

    /**
     * ID对象
     */
    public data class ID(val id: String)
}

/**
 * 将一个整形转换成ID对象
 */
public val Int.ID get() = IBotManager.ID(this.toString())

/**
 * 将一个长整形转换成ID对象
 */
public val Long.ID get() = IBotManager.ID(this.toString())

/**
 * 将一个字符串转换成ID对象
 */
public val String.ID get() = IBotManager.ID(this)