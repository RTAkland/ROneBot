/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.exceptions

/**
 * 在Native平台上使用WSS协议时抛出异常
 */
public class WebsocketProtocolNotSupportedException(message: String) : Exception(message)