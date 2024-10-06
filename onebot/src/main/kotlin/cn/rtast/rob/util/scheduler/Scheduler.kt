/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.util.scheduler

interface Scheduler {

    /**
     * 创建一个定时任务指定延迟开始时间
     * 并且指定一个周期
     */
    fun scheduleTask(task: suspend () -> Unit, delay: Long, period: Long): TaskHandle

    /**
     * 取消一个任务
     */
    fun cancelTask(taskHandle: TaskHandle): Boolean
}