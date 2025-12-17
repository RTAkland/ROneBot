/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/23
 */


package cn.rtast.rob.enums.internal

import kotlinx.serialization.Serializable

/**
 * SDK实例类别/内部使用
 */
@Serializable
public enum class InstanceType {
    Client, Server
}