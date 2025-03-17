/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.enums

public enum class ProjectType(
    public val buildScriptName: String,
    public val mainClassName: String,
) {
    OneBot11("onebot11.build.gradle.kts", "OneBot11.Main.kt"),
    QQBot("qqbot.build.gradle.kts", "QQBot.Main.kt");

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