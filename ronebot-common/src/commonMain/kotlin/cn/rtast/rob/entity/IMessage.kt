/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.entity

import kotlin.uuid.Uuid

/**
 * 基本消息类型
 */
public sealed interface IMessage {
    public var sessionId: Uuid?
}