/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 1:26 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.common

public enum class GradlePlugins(public val idString: String) {
    Shadow("""id("com.gradleup.shadow") version "9.1.0""""),
    Java("""id("java")"""), KotlinJvm("""kotlin("jvm") version "{{KOTLIN_VERSION}}""""),
    KotlinMultiplatform("""kotlin("multiplatform") "{{KOTLIN_VERSION}}"""")
}