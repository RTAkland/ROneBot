/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.enums

import cn.rtast.rob.satori.enums.OPCode.*

/**
 * 标记所有的op代码
 */
public enum class OPCode(public val code: Int) {
    EVENT(0), Ping(1), Pong(2), IDENTIFY(3), READY(4);
}

public fun Int.forCode(): OPCode {
    return when (this) {
        0 -> EVENT
        1 -> Ping
        2 -> Pong
        3 -> IDENTIFY
        4 -> READY
        else -> throw NullPointerException()
    }
}