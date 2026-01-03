/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 00:51
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(DelicateCoroutinesApi::class)

package cn.rtast.rob.scheduler

import kotlinx.coroutines.*
import kotlin.time.Duration

internal actual val schedulerScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)

internal actual fun <T> scheduleTaskInternal(
    botInstances: List<T>,
    task: suspend (List<T>) -> Unit,
    delay: Duration,
    period: Duration,
): TaskHandle {
    val job = GlobalScope.launch {
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