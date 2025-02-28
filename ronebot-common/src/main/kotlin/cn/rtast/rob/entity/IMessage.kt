/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.entity

import java.util.UUID

/**
 * 基本消息类型
 */
sealed interface IMessage {
    /**
     * 会话ID, 用于控制session
     */
    var sessionId: UUID
}