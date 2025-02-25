/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */


package cn.rtast.rob.entity

data class FiredUser(
    override val id: String,
    val isGroup: Boolean,
    val groupId: Long?
) : IFiredUser