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

public interface MessageActionable {
    public suspend fun reply(message: MessageChain): Either<String, SendMessageResponse.SendMessage>
    public suspend fun reply(text: Any): Either<String, SendMessageResponse.SendMessage>
    public suspend fun reaction(faceId: String)
    public suspend fun unsetReaction(faceId: String)
    public suspend fun recall()
}