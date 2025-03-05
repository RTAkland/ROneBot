/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.enums

public enum class ButtonActionType(public val type: Int) {
    MiniApp(0), Callback(1), Command(2);
}

public enum class ButtonPermissionType(public val type: Int) {
    SpecifyUser(0), OnlyAdmin(1), Everyone(2), SpecifyRoleGroup(3);
}

public enum class ButtonActionAnchor(public val type: Int) {
    Normal(1)
}