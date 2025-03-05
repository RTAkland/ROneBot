/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/27
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.scheduler.GlobalCoroutineScheduler

/**
 * 所有模块的入口点
 */
public interface BotFactory {
    /**
     * 记录私聊+群聊指令执行了多少次
     * 只记录成功执行的次数
     * 如果你想让计数器清零可以增加一个
     * 调度器([GlobalCoroutineScheduler]或[cn.rtast.rob.scheduler.BotCoroutineScheduler])
     * 来定时的将执行次数清空设置为0
     */
    public var totalCommandExecutionTimes: Int

    /**
     * 记录私聊指令执行了多少次
     * 只记录成功执行的次数
     */
    public var privateCommandExecutionTimes: Int

    /**
     * 记录群聊指令执行了多少次
     * 只记录成功执行的次数
     */
    public var groupCommandExecutionTimes: Int

    /**
     * 重置私聊指令执行次数
     */
    public fun resetPrivateCommandExecutionTimes() {
        privateCommandExecutionTimes = 0
    }

    /**
     * 重置群聊指令执行次数
     */
    public fun resetGroupCommandExecutionTimes() {
        groupCommandExecutionTimes = 0
    }
}