/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.enums.internal

import kotlinx.serialization.Serializable

@Serializable
public enum class HTTPMethod {
    GET, POST, PUT, DELETE
}