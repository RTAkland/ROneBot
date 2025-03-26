/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.starter.common

public enum class ROneBotTarget(public val targetName: String, public val targetDisplayName: String) {
    Jvm("jvm", "Jvm"),
    LinuxX64("linuxX64", "Linux"),
    MingwX64("mingwX64", "WindowsX64"),
    LinuxArm64("linuxArm64", "Linux ARM64"),
    MacOSX64("macosX64", "MacOSX64"),
    MacOSArm64("macosArm64", "MacOS ARM64");

    public companion object {
        public fun fromString(string: String): ROneBotTarget? {
            return when (string) {
                Jvm.targetName -> Jvm
                LinuxX64.targetName -> LinuxX64
                MingwX64.targetName -> MingwX64
                else -> null
            }
        }
    }
}