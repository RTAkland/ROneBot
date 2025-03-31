/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.enums

import kotlinx.serialization.Serializable

@Serializable
public enum class ButtonActionType(public val type: Int) {
    MiniApp(0), Callback(1), Command(2);
}

@Serializable
public enum class ButtonPermissionType(public val type: Int) {
    SpecifyUser(0), OnlyAdmin(1), Everyone(2), SpecifyRoleGroup(3);
}

@Serializable
public enum class ButtonActionAnchor(public val type: Int) {
    Normal(1)
}