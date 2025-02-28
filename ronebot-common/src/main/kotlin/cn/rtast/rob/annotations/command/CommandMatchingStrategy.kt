/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/9
 */


package cn.rtast.rob.annotations.command

import cn.rtast.rob.enums.MatchingStrategy

/**
 * 标记一个指令是使用[Regex]来解析
 * 还是使用空格分割后解析
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandMatchingStrategy(val mode: MatchingStrategy)