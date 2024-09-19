/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/18
 */


package cn.rtast.rob.entity.internal

import cn.rtast.rob.exceptions.IllegalDelayException
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.MessageChain

interface MessageActionable {

    /**
     * 撤回消息的接口, 提供一个延迟秒数在n秒后撤回消息
     * 在私聊消息中无法撤回对方的消息
     * 如果OneBot实现开启了上报自身消息则可以使用这个方法来撤回自身的消息
     */
    suspend fun revoke(delay: Int) {
        if (delay < 0) {
            throw IllegalDelayException("Delay second(s) must great than 0 or equals to 0! >>> $delay")
        }
    }

    /**
     * 没有延迟秒数的撤回方法
     */
    suspend fun revoke() {
        this.revoke(0)
    }

    /**
     * 使用MessageChain来回复消息
     */
    suspend fun reply(content: MessageChain)

    /**
     * 使用纯文本字符串回复消息
     */
    suspend fun reply(content: String)

    /**
     * 使用CQ码消息链回复
     */
    suspend fun reply(content: CQMessageChain)
}