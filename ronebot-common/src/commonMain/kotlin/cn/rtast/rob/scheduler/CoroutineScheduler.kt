/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */

@file:Suppress("unused")
@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.scheduler

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import kotlinx.coroutines.*
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.time.Duration

/**
 * 为调度器单独创建一个线程池
 */
internal expect val schedulerScope: CoroutineScope

internal expect fun <T> scheduleTaskInternal(
    botInstances: List<T>,
    task: suspend (List<T>) -> Unit,
    delay: Duration,
    period: Duration,
): TaskHandle

/**
 * Bot实例作用域调度器只能访问当前Bot实例
 */
public class BotCoroutineScheduler<T : BaseBotInstance>(
    private val botInstance: T
) : BotScheduler<T> {

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun scheduleTask(task: suspend (T) -> Unit, delay: Duration, period: Duration): TaskHandle {
        return scheduleTaskInternal(listOf(botInstance), { task(it.first()) }, delay, period)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}

/**
 * 全局作用域调度器, 可以访问所有已注册的Bot实例
 */
public class GlobalCoroutineScheduler<T : BaseBotInstance>(
    private val botInstances: List<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : GlobalScheduler<T> {

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun scheduleTask(
        task: suspend (List<T>) -> Unit,
        delay: Duration,
        period: Duration
    ): TaskHandle {
        return scheduleTaskInternal(botInstances, task, delay, period)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}