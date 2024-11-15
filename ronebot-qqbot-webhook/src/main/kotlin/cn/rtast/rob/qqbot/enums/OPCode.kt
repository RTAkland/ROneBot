/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.enums

enum class OPCode(val opCode: Int) {
    Dispatch(0), Heartbeat(1), Identify(2),
    Resume(6), Reconnect(7), InvalidSession(9),
    Hello(10), HeartbeatACK(11), HTTPCallbackACK(12),
    CallbackURLVerify(13)
}