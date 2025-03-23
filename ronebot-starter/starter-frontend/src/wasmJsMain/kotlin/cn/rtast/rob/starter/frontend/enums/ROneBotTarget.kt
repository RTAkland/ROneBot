/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.starter.frontend.enums

public enum class ROneBotTarget(public val targetName: String, public val targetDisplayName: String) {
    Jvm("jvm", "Jvm"), LinuxX64("linuxX64", "Linux"), MingwX64("mingwX64", "Windows")
}