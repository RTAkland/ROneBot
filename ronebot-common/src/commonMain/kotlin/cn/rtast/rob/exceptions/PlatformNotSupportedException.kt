/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.exceptions

/**
 * 平台不支持某个功能时抛出异常
 */
public class PlatformNotSupportedException(message: String) : Exception(message)