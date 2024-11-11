/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.interceptor

enum class InterceptorPriority(val priority: Int) {
    GLOBAL(10), LOCAL(0)
}