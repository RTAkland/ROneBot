/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.onebot.OneBotAction

/**
 * 成员被禁言
 */
public data class RawBanEvent(
    /**
     * 群号
     */
    val groupId: Long,
    /**
     * 操作者的QQ
     */
    val operator: Long,
    /**
     * 时长, 秒
     */
    val duration: Int,
    /**
     * 时间戳
     */
    val time: Long,
    /**
     * 被禁言的人
     */
    val userId: Long,
    val action: OneBotAction
)

/**
 * 成员被解除禁言
 */
public data class RawPardonBanEvent(
    /**
     * 群号
     */
    val groupId: Long,
    /**
     * 操作者QQ
     */
    val operator: Long,
    /**
     * 时长, 但是是解除禁言所以为0
     */
    val duration: Int,
    /**
     * 时间戳
     */
    val time: Long,
    /**
     * 被解除禁言的人的QQ
     */
    val userId: Long,
    val action: OneBotAction
)