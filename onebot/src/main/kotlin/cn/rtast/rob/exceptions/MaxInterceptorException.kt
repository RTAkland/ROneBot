/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.exceptions

/**
 * 当超过最大的指令拦截器时抛出异常
 */
class MaxInterceptorException(override val message: String) : Exception(message)