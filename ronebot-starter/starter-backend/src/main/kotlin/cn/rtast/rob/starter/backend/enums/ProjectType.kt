/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.enums

public enum class ProjectType(
    public val buildScriptName: String,
    public val mainClassName: String,
    public val platformName: String
) {
    OneBot11("buildScript/native.kts", "OneBot11.Main.kt", "ronebot-onebot-v11"),
    QQBot("buildScript/native.kts", "QQBot.Main.kt", "ronebot-qqbot-webhook");

    public companion object {
        public fun fromString(string: String): ProjectType? {
            return when (string) {
                OneBot11.name -> OneBot11
                QQBot.name -> QQBot
                else -> null
            }
        }
    }
}