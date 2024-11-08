/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */

@file:Suppress("unused")

package cn.rtast.rob.scheduler

import cn.rtast.rob.BaseBotInstance
import kotlinx.coroutines.*
import kotlin.time.Duration

class BotCoroutineScheduler<T : BaseBotInstance>(
    private val botInstance: T,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BotScheduler<T> {

    private val scope = CoroutineScope(dispatcher)

    override fun scheduleTask(task: suspend (T) -> Unit, delay: Duration, period: Duration): TaskHandle {
        val job = scope.launch {
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

    override fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}

class GlobalCoroutineScheduler<T : BaseBotInstance>(
    private val botInstances: List<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GlobalScheduler<T> {

    private val scope = CoroutineScope(dispatcher)

    override fun scheduleTask(
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

    override fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}