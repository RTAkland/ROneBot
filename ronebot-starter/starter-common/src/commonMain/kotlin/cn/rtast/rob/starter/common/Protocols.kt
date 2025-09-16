/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 6:46 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.common

public enum class Protocols(
    public val display: String,
    public val status: Status,
    public val description: String,
) {
    OneBot11("onebot11", Status.Stable, "暂缓更新(仅维护)"),
    Milky("milky", Status.Stable, "积极更新");

    public enum class Status {
        Stable, Beta, Alpha
    }
}

public fun String.asProtocol(): Protocols = when (this) {
    Protocols.OneBot11.display -> Protocols.OneBot11
    Protocols.Milky.display -> Protocols.Milky
    else -> throw IllegalStateException("没有这种协议")
}