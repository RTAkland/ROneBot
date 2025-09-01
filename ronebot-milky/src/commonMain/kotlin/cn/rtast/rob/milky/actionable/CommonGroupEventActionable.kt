/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/1/25, 12:05 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.actionable

import arrow.core.Either
import cn.rtast.rob.milky.event.common.Group

/**
 * 公共的群聊消息事件快速操作接口
 */
public interface CommonGroupEventActionable {
    /**
     * 获取群基本信息
     */
    public suspend fun getGroupInfo(): Either<String, Group>
}