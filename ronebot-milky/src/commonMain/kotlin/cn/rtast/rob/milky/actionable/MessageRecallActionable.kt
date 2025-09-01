/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/1/25, 11:59 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

import arrow.core.Either
import cn.rtast.rob.milky.event.common.Message

/**
 * 消息撤回
 */
public interface MessageRecallActionable {
    /**
     * 获取被撤回的消息内容
     */
    public suspend fun getRecalledMessage(): Either<String, Message>
}