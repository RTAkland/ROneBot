/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.enums

internal enum class OPCode(val code: Int) {
    EVENT(0), Ping(1), Pong(2), IDENTIFY(3), READY(4)
}