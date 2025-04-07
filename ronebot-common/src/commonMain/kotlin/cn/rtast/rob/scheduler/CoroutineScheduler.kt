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
import cn.rtast.rob.commonCoroutineScope
import kotlinx.coroutines.*
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.time.Duration

public class BotCoroutineScheduler<T : BaseBotInstance>(
    private val botInstance: T
) : BotScheduler<T> {

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun scheduleTask(task: suspend (T) -> Unit, delay: Duration, period: Duration): TaskHandle {
        val job = commonCoroutineScope.launch {
            try {
                delay(delay)
                while (isActive) {
                    // 如果action未初始化则不进行任何操作, 防止抛出未初始化异常
                    if (botInstance.isActionInitialized) {
                        task(botInstance)
                        delay(period)
                    }
                }
            } catch (_: CancellationException) {
                this.cancel()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return CoroutineTaskHandle(job)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}

public class GlobalCoroutineScheduler<T : BaseBotInstance>(
    private val botInstances: List<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : GlobalScheduler<T> {

    private val scope = CoroutineScope(dispatcher)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun scheduleTask(
        task: suspend (List<T>) -> Unit,
        delay: Duration,
        period: Duration
    ): TaskHandle {
        val job = scope.launch {
            try {
                delay(delay)
                while (isActive) {
                    task(botInstances)
                    delay(period)
                }
            } catch (_: CancellationException) {
                this.cancel()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return CoroutineTaskHandle(job)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}