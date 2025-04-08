/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 10:26
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.common

public enum class Language(public val languageName: String) {
    Kotlin("kotlin"), Java("java");

    public companion object {
        public fun fromName(name: String): Language {
            return when (name.lowercase()) {
                "kotlin" -> Kotlin
                "java" -> Java
                else -> Kotlin
            }
        }
    }
}