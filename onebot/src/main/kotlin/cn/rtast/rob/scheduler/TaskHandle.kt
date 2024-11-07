/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.scheduler

interface TaskHandle {

    /**
     * 确定任务是否已被取消
     */
    val isCancelled: Boolean

    /**
     * 取消任务
     */
    fun cancel(): Boolean
}