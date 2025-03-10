/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

public object Resources {
    public fun load(path: String): ByteArray {
        println("templates/$path")
        return this::class.java.classLoader.getResourceAsStream("templates/$path")!!.use { it.readBytes() }
    }
}