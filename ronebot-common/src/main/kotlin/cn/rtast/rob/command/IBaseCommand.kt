/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.enums.MatchingStrategy

/**
 * 所有子模块的基本抽象命令父类
 * 都要继承此接口
 */
interface IBaseCommand<out G : IGroupMessage, out P : IPrivateMessage> {
    /**
     * 定义指令别名
     */
    val commandNames: List<String>

    /**
     * 在群聊中触触发此接口, 注意: [args]参数是去掉了指令的部分, 如果输入的命令为`/echo Test`
     * 那么args参数的size属性就为1, 内容就是
     * ```kotlin
     * listOf("Test")
     * ```
     */
    suspend fun executeGroup(message: @UnsafeVariance G, args: List<String>)

    /**
     * 群聊中触发此接口并附带匹配到的命令
     */
    suspend fun executeGroup(message: @UnsafeVariance G, args: List<String>, matchedCommand: String)

    /**
     * 在私聊中触发此接口
     */
    suspend fun executePrivate(message: @UnsafeVariance P, args: List<String>)

    /**
     * 私聊中触发此接口并且附带匹配到的命令
     */
    suspend fun executePrivate(message: @UnsafeVariance P, args: List<String>, matchedCommand: String)

    /**
     * 内部使用处理私聊指令
     */
    suspend fun handlePrivate(message: @UnsafeVariance P, matchedCommand: String, matchMode: MatchingStrategy)

    /**
     * 内部使用处理群聊指令
     */
    suspend fun handleGroup(message: @UnsafeVariance G, matchedCommand: String, matchMode: MatchingStrategy)

    /**
     * 私聊拒绝这次的会话内容
     */
    suspend fun @UnsafeVariance P.reject(reason: IMessageChain)

    /**
     * 群聊拒绝这次的会话内容
     */
    suspend fun @UnsafeVariance G.reject(reason: IMessageChain)

    /**
     * 群聊会话接收函数
     */
    suspend fun onGroupSession(msg: @UnsafeVariance G)

    /**
     * 私聊会话接收函数
     */
    suspend fun onPrivateSession(msg: @UnsafeVariance P)

    /**
     * 私聊结束会话
     */
    suspend fun @UnsafeVariance P.skipSession()

    /**
     * 群聊结束会话
     */
    suspend fun @UnsafeVariance G.skipSession()

    /**
     * 群聊开始会话
     */
    suspend fun @UnsafeVariance G.startSession()

    /**
     * 私聊开始会话
     */
    suspend fun @UnsafeVariance P.startSession()
}