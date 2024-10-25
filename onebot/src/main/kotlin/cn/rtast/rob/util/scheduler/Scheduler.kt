/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.util.scheduler

import cn.rtast.rob.BotInstance
import kotlin.time.Duration

/**
 * Bot实例作用域的任务调度器
 */
interface BotScheduler {

    /**
     * 创建一个定时任务指定延迟开始时间
     * 并且指定一个周期, 可以使用`it`操作符来访问
     * Bot实例并作出操作
     * ```kotlin
     * instance1.scheduler.scheduleTask({
     *     println(it.action.getLoginInfo())
     * }, 1000L, 1000L)
     * ```
     */
    fun scheduleTask(task: suspend (BotInstance) -> Unit, delay: Duration, period: Duration): TaskHandle

    /**
     * 取消一个任务
     */
    fun cancelTask(taskHandle: TaskHandle): Boolean
}

/**
 * 全局作用域的任务调度器
 */
interface GlobalScheduler {

    fun scheduleTask(task: suspend (List<BotInstance>) -> Unit, delay: Duration, period: Duration): TaskHandle

    fun cancelTask(taskHandle: TaskHandle): Boolean
}