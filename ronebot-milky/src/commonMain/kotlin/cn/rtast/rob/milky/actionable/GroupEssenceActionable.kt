/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/1/25, 12:28 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

import arrow.core.Either
import cn.rtast.rob.milky.event.group.GetGroupEssenceMessages

/**
 * 群精华
 */
public interface GroupEssenceActionable {

    /**
     * 设置精华消息
     */
    public suspend fun setEssence() {}

    /**
     * 设置精华消息
     */
    public suspend fun setEssence(messageSeq: Long)

    /**
     * 取消设置精华消息
     */
    public suspend fun unsetEssence() {}

    /**
     * 取消设置精华消息
     */
    public suspend fun unsetEssence(messageSeq: Long)

    /**
     * 获取群精华消息
     */
    public suspend fun getGroupEssenceMessages(): Either<String, GetGroupEssenceMessages.GroupEssenceMessages>

    /**
     * 获取群精华消息
     */
    public suspend fun getGroupEssenceMessages(pageIndex: Int): Either<String, GetGroupEssenceMessages.GroupEssenceMessages>

    /**
     * 获取群精华消息
     */
    public suspend fun getGroupEssenceMessages(
        pageSize: Int,
        pageIndex: Int,
    ): Either<String, GetGroupEssenceMessages.GroupEssenceMessages>
}