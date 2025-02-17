/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/18
 */


package cn.rtast.rob.entity

interface ISender {
    val userId: Long
}

interface IPrivateSender : ISender

interface IGroupSender : ISender