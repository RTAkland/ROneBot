/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */

@file:Suppress("unused")

package cn.rtast.rob.util.scheduler

import kotlinx.coroutines.*

class CoroutineScheduler(dispatcher: CoroutineDispatcher = Dispatchers.Default) : Scheduler {

    private val scope = CoroutineScope(dispatcher)

    override fun scheduleTask(task: suspend () -> Unit, delay: Long, period: Long): TaskHandle {
        val job = scope.launch {
            delay(delay)
            while (isActive) {
                task()
                delay(period)
            }
        }
        return CoroutineTaskHandle(job)
    }

    override fun cancelTask(taskHandle: TaskHandle): Boolean {
        return taskHandle.cancel()
    }
}