/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.session.GroupSessionStruct
import cn.rtast.rob.session.PrivateSessionStruct

/**
 * 所有子模块的基本抽象命令父类
 * 都要继承此接口
 */
public interface IBaseCommand<G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 定义指令别名
     */
    public val commandNames: List<String>

    /**
     * 在群聊中触触发此接口, 注意: [args]参数是去掉了指令的部分, 如果输入的命令为`/echo Test`
     * 那么args参数的size属性就为1, 内容就是
     * ```kotlin
     * listOf("Test")
     * ```
     */
    public suspend fun executeGroup(message: G, args: List<String>)

    /**
     * 在私聊中触发此接口
     */
    public suspend fun executePrivate(message: P, args: List<String>)

    /**
     * 临时消息
     */
    public suspend fun executeTemp(message: P, args: List<String>) {}

    /**
     * 内部使用处理私聊指令
     */
    public suspend fun handlePrivate(message: P, matchedCommand: String)

    /**
     * 内部使用处理群聊指令
     */
    public suspend fun handleGroup(message: G, matchedCommand: String)

    public suspend fun startGroupSession(message: G, block: suspend (GroupSessionStruct<G>) -> Unit)
    public suspend fun startPrivateSession(message: P, block: suspend (PrivateSessionStruct<P>) -> Unit)
}