/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.starter.common

public enum class ROneBotPlatform(
    public val targetName: String,
    public val targetDisplayName: String,
    public val replaceName: String,
) {
    Jvm("jvm", "Jvm", "{{JVM}}"),
    LinuxX64("linuxX64", "Linux", "{{LINUX}}"),
    MingwX64("mingwX64", "WindowsX64", "{{MINGW}}"),
    LinuxArm64("linuxArm64", "Linux ARM64", "LINUX_ARM"),
    MacOSArm64("macosArm64", "MacOS ARM64", "{{MACOS_ARM}}");

    public companion object {
        public fun fromString(string: String): ROneBotPlatform? {
            return when (string) {
                Jvm.targetName -> Jvm
                LinuxX64.targetName -> LinuxX64
                MingwX64.targetName -> MingwX64
                else -> null
            }
        }
    }
}