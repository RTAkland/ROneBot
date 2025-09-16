/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 6:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.backend.generators

interface BaseGenerator {
    fun generate(): ByteArray
    fun load(path: String): ByteArray
    fun loadAsString(path: String): String
}