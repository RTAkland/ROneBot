/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */

@file:Suppress("unused")

package cn.rtast.rob.scheduler

import kotlinx.coroutines.Job

class CoroutineTaskHandle(private val job: Job) : TaskHandle {
    override val isCancelled: Boolean
        get() = job.isCancelled

    override suspend fun cancel(): Boolean {
        return if (!job.isCancelled) {
            job.cancel()
            true
        } else {
            false
        }
    }
}