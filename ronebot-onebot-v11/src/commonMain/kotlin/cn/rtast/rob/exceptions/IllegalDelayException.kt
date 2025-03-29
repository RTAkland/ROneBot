/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/18
 */


package cn.rtast.rob.exceptions

/**
 * 延迟消息不合法时抛出异常
 */
public class IllegalDelayException(override val message: String) : Exception(message)