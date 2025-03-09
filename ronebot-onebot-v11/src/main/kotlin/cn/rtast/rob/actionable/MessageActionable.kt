/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/18
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.actionable

import cn.rtast.rob.event.raw.lagrange.ForwardMessageId
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.onebot.CQMessageChain
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.onebot.NodeMessageChain
import cn.rtast.rob.segment.Segment
import kotlin.time.Duration

/**
 * 对一个私聊消息快速进行操作, 例如回复、撤回、已读等, 并且
 * 部分方法提供了异步的调用方式
 */
public interface MessageActionable {

    /**
     * 撤回消息的接口, 提供一个延迟秒数在n秒后撤回消息
     * 在私聊消息中无法撤回对方的消息
     * 如果OneBot实现开启了上报自身消息则可以使用这个方法来撤回自身的消息
     * 单位: 秒
     */
    public suspend fun revoke(delay: Int)

    /**
     * 没有延迟秒数的撤回方法
     */
    public suspend fun revoke() {
        this.revoke(0)
    }

    /**
     * 撤回发送来的消息
     * 但是使用[Duration]对象
     */
    public suspend fun revoke(delay: Duration)

    /**
     * 撤回指定消息ID的消息但是没有延迟
     *
     */
    public suspend fun revokeId(messageId: Long) {
        this.revokeId(0, messageId)
    }

    /**
     * 撤回指定消息ID的消息但是有延迟
     * 单位: 秒
     */
    public suspend fun revokeId(delay: Int, messageId: Long)

    /**
     * 撤回发送来的消息
     * 但是使用[Duration]对象
     */
    public suspend fun revokeId(delay: Duration, messageId: Long)

    /**
     * 使用[Segment]进行回复
     */
    public suspend fun reply(content: Segment): Long?

    /**
     * 使用[Segment]进行回复, 但是异步
     */
    public suspend fun replyAsync(content: Segment)

    /**
     * 使用[Segment]列表回复
     */
    public suspend fun reply(content: List<Segment>): Long?

    /**
     * 使用[Segment]列表回复, 但是异步
     */
    public suspend fun replyAsync(content: List<Segment>)

    /**
     * 使用MessageChain来回复消息
     */
    public suspend fun reply(content: MessageChain): Long?

    /**
     * 使用MessageChain来回复消息, 但是异步
     */
    public suspend fun replyAsync(content: MessageChain)

    /**
     * 使用纯文本字符串回复消息
     */
    public suspend fun reply(content: String): Long?

    /**
     * 使用纯文本字符串回复消息, 但是异步
     */
    public suspend fun replyAsync(content: String)

    /**
     * 使用CQ码消息链回复
     */
    @Deprecated("CQ码已被弃用")
    public suspend fun reply(content: CQMessageChain): Long?

    /**
     * 使用CQ码消息链回复, 但是异步
     */
    public suspend fun replyAsync(content: CQMessageChain)

    /**
     * 使用转发消息链回复, 但是并不会真正的回复
     * 而是发出一个普通的合并消息转发链
     */
    public suspend fun reply(content: NodeMessageChain): ForwardMessageId.ForwardMessageId?

    /**
     * 默认使用异步发送合并转发消息链和函数
     */
    public suspend fun replyAsync(content: NodeMessageChain)

    /**
     * 将一个消息标记为已读
     */
    public suspend fun markAsRead()

    /**
     * 发送消息
     */
    public suspend fun sendMessageAsync(content: MessageChain)

    /**
     * 发送消息
     */
    public suspend fun sendMessage(content: MessageChain): Long?

    /**
     * 发送消息
     */
    public suspend fun sendMessageAsync(content: String)

    /**
     * 发送消息
     */
    public suspend fun sendMessage(content: String): Long?

    /**
     * 发送消息
     */
    public suspend fun sendMessageAsync(content: Segment)

    /**
     * 发送消息
     */
    public suspend fun sendMessage(content: Segment): Long?

    /**
     * 发送消息
     */
    public suspend fun sendMessageAsync(content: List<Segment>)

    /**
     * 发送消息
     */
    public suspend fun sendMessage(content: List<Segment>): Long?
}

/**
 * 对一个群聊消息进行快速操作, 继承了所有饲料消息的特性
 * 并且新增了群聊中特有的操作例如: 回应、设置精华等
 */
public interface GroupMessageActionable : MessageActionable {

    /**
     * 此API是Lagrange.OneBot的拓展API
     * 对一个消息使用reaction
     */
    public suspend fun reaction(code: QQFace) {
        this.reaction(code.id.toString())
    }

    /**
     * 此API是Lagrange.OneBot的拓展API
     * 使用不在QQFace枚举类中的表情ID进行回应
     */
    public suspend fun reaction(code: String)

    /**
     * 此API是Lagrange.OneBot的拓展API
     * 取消对某个指定表情和指定消息的取消reaction
     */
    public suspend fun unsetReaction(code: QQFace) {
        this.unsetReaction(code.id.toString())
    }

    /**
     * 此API是Lagrange.OneBot的拓展API
     * 使用不在QQFace枚举类中的表情ID进行取消回应
     */
    public suspend fun unsetReaction(code: String)

    /**
     * 此API是Lagrange.OneBot的拓展API
     * 将此消息设置群精华
     */
    public suspend fun setEssence()

    /**
     * 此API是Lagrange.OneBot的拓展API
     * 将此消息从群精华中移除
     */
    public suspend fun deleteEssence()
}