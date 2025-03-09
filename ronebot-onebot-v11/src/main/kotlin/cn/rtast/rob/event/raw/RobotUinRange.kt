/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.event.raw

/**
 * QQ官方机器人的QQ号范围区间
 */
public data class RobotUinRange(
    val data: List<UinRange>
) {
    public data class UinRange(
        /**
         * 最大
         */
        val maxUin: String,
        /**
         * 最小
         */
        val minUin: String
    )
}