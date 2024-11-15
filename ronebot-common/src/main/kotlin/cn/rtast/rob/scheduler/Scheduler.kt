/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.scheduler

import cn.rtast.rob.BaseBotInstance
import kotlin.time.Duration

/**
 * Bot实例作用域的任务调度器
 * 使用泛型来避免用户手动进行类型转换
 */
interface BotScheduler<T : BaseBotInstance> {

    /**
     * 创建一个定时任务指定延迟开始时间
     * 并且指定一个周期, 可以使用`it`操作符来访问
     * Bot实例并作出操作
     * e.g.
     * ```kotlin
     * instance1.scheduler.scheduleTask({
     *     println(it.action.getLoginInfo())
     * }, 1.seconds, 5.seconds)
     * ```
     */
    suspend fun scheduleTask(task: suspend (T) -> Unit, delay: Duration, period: Duration): TaskHandle

    /**
     * 取消一个任务
     */
    suspend fun cancelTask(taskHandle: TaskHandle): Boolean
}

/**
 * 全局作用域的任务调度器
 */
interface GlobalScheduler<T : BaseBotInstance> {

    suspend fun scheduleTask(task: suspend (List<T>) -> Unit, delay: Duration, period: Duration): TaskHandle

    suspend fun cancelTask(taskHandle: TaskHandle): Boolean
}