/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/4
 */


package cn.rtast.rob.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

/**
 * 一个简单的logger包装
 * 如果想屏蔽ROneBot的日志输出可以在logback
 * 的xml中将ROneBot的等级设置成`OFF`
 */
public object Logger {
    public fun getLogger(): Logger {
        return LoggerFactory.getLogger("ROneBot").apply { atLevel(Level.INFO) }
    }
}