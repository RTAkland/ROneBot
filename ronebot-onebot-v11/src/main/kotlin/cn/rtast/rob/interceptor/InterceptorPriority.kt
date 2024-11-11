/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.interceptor

enum class InterceptorPriority(val priority: Int) {
    HIGHEST(10), HIGH(5), LOW(2), LOWEST(0);

    companion object {
        fun forPriority(priority: Int): InterceptorPriority {
            return when (priority) {
                HIGHEST.priority -> HIGHEST
                HIGH.priority -> HIGH
                LOW.priority -> LOW
                else -> LOWEST
            }
        }
    }
}