/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.actionable

/**
 * 对一个事件快速处理, 所有操作均为异步操作
 */
interface RequestEventActionable {

    /**
     * 通意加好/加群友请求
     */
    suspend fun approve()

    /**
     * 拒绝请求加好友/加群
     */
    suspend fun reject(remark: String?)
}