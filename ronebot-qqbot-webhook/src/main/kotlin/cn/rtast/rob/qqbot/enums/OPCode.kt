/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.enums

public enum class OPCode(public val opCode: Int) {
    Dispatch(0), Heartbeat(1), Identify(2),
    Resume(6), Reconnect(7), InvalidSession(9),
    Hello(10), HeartbeatACK(11), HTTPCallbackACK(12),
    CallbackURLVerify(13)
}