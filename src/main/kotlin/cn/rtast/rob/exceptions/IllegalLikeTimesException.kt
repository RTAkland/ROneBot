/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/18
 */


package cn.rtast.rob.exceptions

/**
 * 当最大赞数超过10时会抛出异常
 */
class IllegalLikeTimesException(override val message: String) : Exception(message)