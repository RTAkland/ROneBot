/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */

@file:Suppress("unused")

package cn.rtast.rob.util.scheduler

import cn.rtast.rob.BotInstance
import kotlinx.coroutines.*

class CoroutineScheduler(
    private val botInstance: BotInstance,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Scheduler {

    private val scope = CoroutineScope(dispatcher)

    override fun scheduleTask(task: suspend () -> Unit, delay: Long, period: Long): TaskHandle {
        val job = scope.launch {
            delay(delay)
            while (isActive) {
                // 如果action未初始化则不进行任何操作, 防止抛出未初始化异常
                if (botInstance.isActionInitialized) {
                    task()
                    delay(period)
                }
            }
        }
        return CoroutineTaskHandle(job)
    }

    override fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}