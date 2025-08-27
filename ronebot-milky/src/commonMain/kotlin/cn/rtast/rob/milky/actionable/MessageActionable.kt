/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

import arrow.core.Either
import cn.rtast.rob.milky.event.message.SendMessageResponse
import cn.rtast.rob.milky.milky.MessageChain

/**
 * 对消息执行快捷操作
 */
public interface MessageActionable {
    /**
     * 回复消息
     * @param message 消息链
     */
    public suspend fun reply(message: MessageChain): Either<String, SendMessageResponse.SendMessage>

    /**
     * 回复消息
     * @param text 任意类型的纯文本消息
     */
    public suspend fun reply(text: Any): Either<String, SendMessageResponse.SendMessage>

    /**
     * 使用表情回应消息
     */
    public suspend fun reaction(faceId: String)

    /**
     * 将指定的表情ID从这个消息的回应中移除
     */
    public suspend fun unsetReaction(faceId: String)

    /**
     * 撤回消息
     */
    public suspend fun recall()
}