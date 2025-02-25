/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/17
 */

@file:Suppress("EnumEntryName", "unused")

package cn.rtast.rob.enums.internal

/**
 * 定义了任务的两种状态但是有些OneBot实现提供的可能不是
 * 这两种所以有些数据类实体用的依旧是字符串类型只有少部分
 * 的数据类实体用到了这个枚举类
 */
enum class ActionStatus {
    failed, ok
}