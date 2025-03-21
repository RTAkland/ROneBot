/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/12
 */

package cn.rtast.rob.onebot.dsl

/**
 * 防止dsl scope滥用
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class MessageChainDsl