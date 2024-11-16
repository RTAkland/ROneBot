/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.enums

enum class ButtonActionType(val type: Int) {
    MiniApp(0), Callback(1), Command(2);
}

enum class ButtonPermissionType(val type: Int) {
    SpecifyUser(0), OnlyAdmin(1), Everyone(2), SpecifyRoleGroup(3);
}

enum class ButtonActionAnchor(val type: Int) {
    Normal(1)
}