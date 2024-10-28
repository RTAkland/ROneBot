/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.llonebot

data class RobotUinRange(
    val data: List<UinRange>
) {
    data class UinRange(
        val maxUin: String,
        val minUin: String
    )
}