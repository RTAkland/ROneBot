/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.actionable

/**
 * 对一个事件快速处理, 所有操作均为异步操作
 */
public interface RequestEventActionable {

    /**
     * 通意加好/加群友请求
     */
    public suspend fun approve()

    /**
     * 拒绝请求加好友/加群
     */
    public suspend fun reject(remark: String?)
}